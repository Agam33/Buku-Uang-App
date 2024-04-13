package com.ra.bkuang.features.transaction.domain.usecase.transfer.impl

import com.ra.bkuang.features.transaction.data.mapper.toModel
import com.ra.bkuang.features.transaction.domain.TransferRepository
import com.ra.bkuang.features.transaction.domain.model.TransferModel
import com.ra.bkuang.features.transaction.domain.usecase.transfer.GetTransferById
import java.util.UUID
import javax.inject.Inject

class GetTransferByIdImpl @Inject constructor(
  private val repository: TransferRepository
): GetTransferById {
  override suspend fun invoke(uuid: UUID): TransferModel {
    return repository.findById(uuid).toModel()
  }
}