package com.ra.bkuang.features.transaction.domain.usecase.transfer.impl

import com.ra.bkuang.features.transaction.domain.model.TransferModel
import com.ra.bkuang.features.transaction.domain.repository.TransferRepository
import com.ra.bkuang.features.transaction.domain.usecase.transfer.GetTransferByIdUseCase
import java.util.UUID
import javax.inject.Inject

class GetTransferByIdUseCaseImpl @Inject constructor(
  private val repository: TransferRepository
): GetTransferByIdUseCase {

  override suspend fun invoke(uuid: String): TransferModel {
    return repository.findById(UUID.fromString(uuid))
  }
}