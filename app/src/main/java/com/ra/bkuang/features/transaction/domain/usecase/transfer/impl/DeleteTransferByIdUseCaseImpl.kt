package com.ra.bkuang.features.transaction.domain.usecase.transfer.impl

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.transaction.domain.TransferRepository
import com.ra.bkuang.features.transaction.domain.usecase.transfer.DeleteTransferByIdUseCase
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class DeleteTransferByIdUseCaseImpl @Inject constructor(
  private val repository: TransferRepository
): DeleteTransferByIdUseCase {

  override fun invoke(uuid: UUID): Flow<Result<Boolean>> {
    return repository.delete(uuid)
  }
}