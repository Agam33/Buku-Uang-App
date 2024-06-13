package com.ra.bkuang.features.transaction.domain.usecase.transfer

import com.ra.bkuang.common.util.Result
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface DeleteTransferByIdUseCase {
  operator fun invoke(uuid: UUID): Flow<Result<Boolean>>
}