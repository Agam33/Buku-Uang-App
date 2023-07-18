package com.ra.budgetplan.domain.usecase.transaksi.transfer.impl

import com.ra.budgetplan.domain.mapper.toModel
import com.ra.budgetplan.domain.model.TransferModel
import com.ra.budgetplan.domain.repository.TransferRepository
import com.ra.budgetplan.domain.usecase.transaksi.transfer.GetTransferById
import java.util.UUID
import javax.inject.Inject

class GetTransferByIdImpl @Inject constructor(
  private val repository: TransferRepository
): GetTransferById {
  override suspend fun invoke(uuid: UUID): TransferModel {
    return repository.findById(uuid).toModel()
  }
}