package com.ra.bkuang.domain.usecase.transaksi.transfer.impl

import com.ra.bkuang.domain.mapper.toEntity
import com.ra.bkuang.domain.mapper.toModel
import com.ra.bkuang.domain.model.TransferModel
import com.ra.bkuang.domain.repository.AkunRepository
import com.ra.bkuang.domain.repository.TransferRepository
import com.ra.bkuang.domain.usecase.transaksi.transfer.SaveTransfer
import com.ra.bkuang.domain.util.ResourceState
import javax.inject.Inject

class SaveTransferImpl @Inject constructor(
  private val transferRepository: TransferRepository,
  private val akunRepository: AkunRepository
): SaveTransfer {

  override suspend fun invoke(transferModel: TransferModel): ResourceState {
    return try {
      val toAccount = akunRepository.findById(transferModel.idToAkun).toModel()
      val fromAccount = akunRepository.findById(transferModel.idFromAkun).toModel()

      toAccount.total += transferModel.jumlah
      fromAccount.total -= transferModel.jumlah

      transferRepository.save(transferModel.toEntity())

      akunRepository.update(toAccount.toEntity())
      akunRepository.update(fromAccount.toEntity())

      ResourceState.SUCCESS
    } catch (e: Exception) {
      ResourceState.FAILED
    }
  }
}