package com.ra.bkuang.domain.usecase.transaksi.transfer.impl

import com.ra.bkuang.domain.mapper.toEntity
import com.ra.bkuang.domain.mapper.toModel
import com.ra.bkuang.domain.model.TransferModel
import com.ra.bkuang.domain.repository.AkunRepository
import com.ra.bkuang.domain.repository.TransferRepository
import com.ra.bkuang.domain.usecase.transaksi.transfer.UpdateTransfer
import com.ra.bkuang.domain.util.ResourceState
import javax.inject.Inject

class UpdateTransferImpl @Inject constructor(
  private val respository: TransferRepository,
  private val accountRepository: AkunRepository
): UpdateTransfer {
  override suspend fun invoke(
    newTransferModel: TransferModel,
    oldTransferModel: TransferModel
  ): ResourceState {
    return try {
      respository.update(newTransferModel.toEntity())

      val newFromAccount = accountRepository.findById(newTransferModel.idFromAkun).toModel()
      val newToAccount = accountRepository.findById(newTransferModel.idToAkun).toModel()

      newFromAccount.total -= newTransferModel.jumlah
      newToAccount.total += newTransferModel.jumlah

      accountRepository.update(newFromAccount.toEntity())
      accountRepository.update(newToAccount.toEntity())

      val fromAccount = accountRepository.findById(oldTransferModel.idFromAkun).toModel()
      val toAccount = accountRepository.findById(oldTransferModel.idToAkun).toModel()

      fromAccount.total += oldTransferModel.jumlah
      toAccount.total -= oldTransferModel.jumlah

      accountRepository.update(fromAccount.toEntity())
      accountRepository.update(toAccount.toEntity())

      ResourceState.SUCCESS
    } catch (e: Exception) {
     ResourceState.FAILED
    }
  }
}