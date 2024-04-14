package com.ra.bkuang.features.transaction.domain.usecase.transfer.impl

import com.ra.bkuang.common.util.ResourceState
import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.account.domain.AkunRepository
import com.ra.bkuang.features.transaction.domain.TransferRepository
import com.ra.bkuang.features.transaction.domain.model.TransferModel
import com.ra.bkuang.features.transaction.domain.usecase.transfer.SaveTransfer
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveTransferImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispather: CoroutineDispatcher,
  private val transferRepository: TransferRepository,
  private val akunRepository: AkunRepository
): SaveTransfer {

  override suspend fun invoke(transferModel: TransferModel): ResourceState = withContext(ioDispather) {
    return@withContext try {
      val toAccount = akunRepository.findById(transferModel.idToAkun)
      val fromAccount = akunRepository.findById(transferModel.idFromAkun)

      toAccount.total += transferModel.jumlah
      fromAccount.total -= transferModel.jumlah

      transferRepository.save(transferModel)

      akunRepository.update(toAccount)
      akunRepository.update(fromAccount)

      ResourceState.SUCCESS
    } catch (e: Exception) {
      ResourceState.FAILED
    }
  }
}