package com.ra.bkuang.features.transaction.domain.usecase.transfer.impl

import com.ra.bkuang.features.account.data.mapper.toEntity
import com.ra.bkuang.features.account.data.mapper.toModel
import com.ra.bkuang.features.transaction.domain.model.TransferModel
import com.ra.bkuang.features.account.domain.AkunRepository
import com.ra.bkuang.features.transaction.domain.TransferRepository
import com.ra.bkuang.features.transaction.domain.usecase.transfer.UpdateTransfer
import com.ra.bkuang.common.util.ResourceState
import com.ra.bkuang.features.transaction.data.mapper.toEntity
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

      val newFromAccount = accountRepository.findById(newTransferModel.idFromAkun)
      val newToAccount = accountRepository.findById(newTransferModel.idToAkun)

      newFromAccount.total -= newTransferModel.jumlah
      newToAccount.total += newTransferModel.jumlah

      accountRepository.update(newFromAccount)
      accountRepository.update(newToAccount)

      val fromAccount = accountRepository.findById(oldTransferModel.idFromAkun)
      val toAccount = accountRepository.findById(oldTransferModel.idToAkun)

      fromAccount.total += oldTransferModel.jumlah
      toAccount.total -= oldTransferModel.jumlah

      accountRepository.update(fromAccount)
      accountRepository.update(toAccount)

      ResourceState.SUCCESS
    } catch (e: Exception) {
     ResourceState.FAILED
    }
  }
}