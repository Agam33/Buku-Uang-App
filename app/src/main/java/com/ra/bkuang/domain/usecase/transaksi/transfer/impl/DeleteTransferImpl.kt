package com.ra.bkuang.domain.usecase.transaksi.transfer.impl

import com.ra.bkuang.domain.mapper.toEntity
import com.ra.bkuang.domain.model.TransferModel
import com.ra.bkuang.domain.repository.TransferRepository
import com.ra.bkuang.domain.usecase.transaksi.transfer.DeleteTransfer
import com.ra.bkuang.util.ResourceState
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