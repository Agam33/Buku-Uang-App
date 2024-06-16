package com.ra.bkuang.features.transaction.domain.usecase.transfer

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.transaction.domain.model.TransferModel
import kotlinx.coroutines.flow.Flow

interface UpdateTransferUseCase {
  fun invoke(newTransferModel: TransferModel, oldTransferModel: TransferModel): Flow<Result<Boolean>>
}