package com.ra.bkuang.features.transaction.domain.usecase.pendapatan.impl

import com.ra.bkuang.features.account.data.mapper.toEntity
import com.ra.bkuang.features.account.data.mapper.toModel
import com.ra.bkuang.features.transaction.domain.model.PendapatanModel
import com.ra.bkuang.features.account.domain.AkunRepository
import com.ra.bkuang.features.transaction.domain.PendapatanRepository
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.UpdatePendapatan
import com.ra.bkuang.common.util.ResourceState
import com.ra.bkuang.features.transaction.data.mapper.toEntity
import javax.inject.Inject

class UpdatePendapatanImpl @Inject constructor(
  private val repository: PendapatanRepository,
  private val accountRepository: AkunRepository
): UpdatePendapatan {
  override suspend fun invoke(newPendapatanModel: PendapatanModel, oldPendapatanModel: PendapatanModel): ResourceState {
    return try {
      repository.update(newPendapatanModel.toEntity())

      val newAccount = accountRepository.findById(newPendapatanModel.idAkun).toModel()

      newAccount.total += newPendapatanModel.jumlah

      accountRepository.update(newAccount.toEntity())

      val oldAccount = accountRepository.findById(oldPendapatanModel.idAkun).toModel()

      oldAccount.total -= oldPendapatanModel.jumlah

      accountRepository.update(oldAccount.toEntity())

      ResourceState.SUCCESS
    } catch (e: Exception) {
      ResourceState.FAILED
    }
  }
}