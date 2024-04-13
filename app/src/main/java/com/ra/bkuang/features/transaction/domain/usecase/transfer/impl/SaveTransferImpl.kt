package com.ra.bkuang.features.transaction.domain.usecase.transfer.impl

import com.ra.bkuang.features.account.data.mapper.toEntity
import com.ra.bkuang.features.account.data.mapper.toModel
import com.ra.bkuang.features.transaction.domain.model.TransferModel
import com.ra.bkuang.features.account.domain.AkunRepository
import com.ra.bkuang.features.transaction.domain.TransferRepository
import com.ra.bkuang.features.transaction.domain.usecase.transfer.SaveTransfer
import com.ra.bkuang.common.util.ResourceState
import com.ra.bkuang.features.transaction.data.mapper.toEntity
import javax.inject.Inject

class SaveTransferImpl @Inject constructor(
  private val transferRepository: TransferRepository,
  private val akunRepository: AkunRepository
): SaveTransfer {

  override suspend fun invoke(transferModel: TransferModel): ResourceState {
    return try {
      val toAccount = akunRepository.findById(transferModel.idToAkun)
      val fromAccount = akunRepository.findById(transferModel.idFromAkun)

      toAccount.total += transferModel.jumlah
      fromAccount.total -= transferModel.jumlah

      transferRepository.save(transferModel.toEntity())

      akunRepository.update(toAccount)
      akunRepository.update(fromAccount)

      ResourceState.SUCCESS
    } catch (e: Exception) {
      ResourceState.FAILED
    }
  }
}