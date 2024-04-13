package com.ra.bkuang.features.transaction.domain.usecase.pendapatan.impl

import com.ra.bkuang.common.util.ResourceState
import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.account.domain.AkunRepository
import com.ra.bkuang.features.transaction.data.mapper.toEntity
import com.ra.bkuang.features.transaction.domain.PendapatanRepository
import com.ra.bkuang.features.transaction.domain.model.PendapatanModel
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.UpdatePendapatan
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdatePendapatanImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispather: CoroutineDispatcher,
  private val repository: PendapatanRepository,
  private val accountRepository: AkunRepository
): UpdatePendapatan {
  override suspend fun invoke(newPendapatanModel: PendapatanModel, oldPendapatanModel: PendapatanModel): ResourceState = withContext(ioDispather) {
    return@withContext try {
      repository.update(newPendapatanModel)

      val newAccount = accountRepository.findById(newPendapatanModel.idAkun)

      newAccount.total += newPendapatanModel.jumlah

      accountRepository.update(newAccount)

      val oldAccount = accountRepository.findById(oldPendapatanModel.idAkun)

      oldAccount.total -= oldPendapatanModel.jumlah

      accountRepository.update(oldAccount)

      ResourceState.SUCCESS
    } catch (e: Exception) {
      ResourceState.FAILED
    }
  }
}