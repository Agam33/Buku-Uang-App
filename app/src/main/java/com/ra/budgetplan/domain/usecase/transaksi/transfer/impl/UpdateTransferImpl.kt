package com.ra.budgetplan.domain.usecase.transaksi.transfer.impl

import com.ra.budgetplan.domain.mapper.toEntity
import com.ra.budgetplan.domain.model.TransferModel
import com.ra.budgetplan.domain.repository.TransferRepository
import com.ra.budgetplan.domain.usecase.transaksi.transfer.UpdateTransfer
import javax.inject.Inject

class UpdateTransferImpl @Inject constructor(
  private val respository: TransferRepository
): UpdateTransfer {
  override suspend fun invoke(transferModel: TransferModel) {
    return respository.update(transferModel.toEntity())
  }
}