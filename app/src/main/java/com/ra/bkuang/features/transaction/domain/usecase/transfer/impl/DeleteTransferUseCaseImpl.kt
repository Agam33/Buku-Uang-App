package com.ra.bkuang.features.transaction.domain.usecase.transfer.impl

import com.ra.bkuang.common.util.ResourceState
import com.ra.bkuang.features.transaction.domain.TransferRepository
import com.ra.bkuang.features.transaction.domain.model.TransferModel
import com.ra.bkuang.features.transaction.domain.usecase.transfer.DeleteTransferUseCase
import javax.inject.Inject

class DeleteTransferUseCaseImpl @Inject constructor(
  private val repository: TransferRepository
): DeleteTransferUseCase {
  override suspend fun invoke(transferModel: TransferModel): ResourceState {
    return try {
      repository.delete(transferModel)
      ResourceState.SUCCESS
    } catch (e: Exception) {
      ResourceState.FAILED
    }
  }
}