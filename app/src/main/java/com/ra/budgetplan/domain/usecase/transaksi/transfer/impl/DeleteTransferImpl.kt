package com.ra.budgetplan.domain.usecase.transaksi.transfer.impl

import com.ra.budgetplan.domain.mapper.toEntity
import com.ra.budgetplan.domain.model.TransferModel
import com.ra.budgetplan.domain.repository.TransferRepository
import com.ra.budgetplan.domain.usecase.transaksi.transfer.DeleteTransfer
import javax.inject.Inject

class DeleteTransferImpl @Inject constructor(
  private val repository: TransferRepository
): DeleteTransfer {
  override suspend fun invoke(transferModel: TransferModel) {
    return repository.delete(transferModel.toEntity())
  }
}