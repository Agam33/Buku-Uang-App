package com.ra.bkuang.features.transaction.domain.usecase.transfer.impl

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.transaction.domain.TransferRepository
import com.ra.bkuang.features.transaction.domain.model.TransferModel
import com.ra.bkuang.features.transaction.domain.usecase.transfer.UpdateTransferUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateTransferUseCaseImpl @Inject constructor(
  private val respository: TransferRepository,
): UpdateTransferUseCase {
  override fun invoke(
    newTransferModel: TransferModel,
    oldTransferModel: TransferModel
  ): Flow<Result<Boolean>> {
    return respository.update(newTransferModel, oldTransferModel)
  }
}