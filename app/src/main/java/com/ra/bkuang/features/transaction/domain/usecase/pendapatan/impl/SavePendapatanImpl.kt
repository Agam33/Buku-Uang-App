package com.ra.bkuang.features.transaction.domain.usecase.pendapatan.impl

import com.ra.bkuang.common.util.ResourceState
import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.account.domain.AkunRepository
import com.ra.bkuang.features.transaction.domain.PendapatanRepository
import com.ra.bkuang.features.transaction.domain.model.PendapatanModel
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.SavePendapatan
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SavePendapatanImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispather: CoroutineDispatcher,
  private val pendapatanRepository: PendapatanRepository,
  private val akunRepository: AkunRepository
): SavePendapatan {
  override suspend fun invoke(pendapatanModel: PendapatanModel): ResourceState = withContext(ioDispather) {
    return@withContext try {
      val account = akunRepository.findById(pendapatanModel.idAkun)

      account.total += pendapatanModel.jumlah

      akunRepository.update(account)

      pendapatanRepository.save(pendapatanModel)

      ResourceState.SUCCESS
    } catch (e: Exception) {
      ResourceState.FAILED
    }
  }
}