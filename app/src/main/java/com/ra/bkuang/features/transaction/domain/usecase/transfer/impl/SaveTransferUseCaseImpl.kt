package com.ra.bkuang.features.transaction.domain.usecase.transfer.impl

import com.ra.bkuang.common.util.ResourceState
import com.ra.bkuang.features.account.domain.AkunRepository
import com.ra.bkuang.features.transaction.domain.TransferRepository
import com.ra.bkuang.features.transaction.domain.model.TransferModel
import com.ra.bkuang.features.transaction.domain.usecase.transfer.SaveTransferUseCase
import javax.inject.Inject

class SaveTransferUseCaseImpl @Inject constructor(
  private val transferRepository: TransferRepository,
  private val akunRepository: AkunRepository
): SaveTransferUseCase {

  override suspend fun invoke(transferModel: TransferModel): ResourceState {
    return try {
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