package com.ra.budgetplan.domain.usecase.transaksi.transfer.impl

import com.ra.budgetplan.domain.mapper.toEntity
import com.ra.budgetplan.domain.model.TransferModel
import com.ra.budgetplan.domain.repository.TransferRepository
import com.ra.budgetplan.domain.usecase.transaksi.transfer.DeleteTransfer
import com.ra.budgetplan.util.ResourceState
import javax.inject.Inject

class DeleteTransferImpl @Inject constructor(
  private val repository: TransferRepository
): DeleteTransfer {
  override suspend fun invoke(transferModel: TransferModel): ResourceState {
    return try {
      repository.delete(transferModel.toEntity())
      ResourceState.SUCCESS
    } catch (e: Exception) {
      ResourceState.FAILED
    }
  }
}