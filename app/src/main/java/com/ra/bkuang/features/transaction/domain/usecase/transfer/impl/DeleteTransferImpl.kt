package com.ra.bkuang.features.transaction.domain.usecase.transfer.impl

import com.ra.bkuang.common.util.ResourceState
import com.ra.bkuang.features.transaction.data.mapper.toEntity
import com.ra.bkuang.features.transaction.domain.TransferRepository
import com.ra.bkuang.features.transaction.domain.model.TransferModel
import com.ra.bkuang.features.transaction.domain.usecase.transfer.DeleteTransfer
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