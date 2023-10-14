package com.ra.bkuang.domain.usecase.transaksi.transfer.impl

import com.ra.bkuang.domain.mapper.toModel
import com.ra.bkuang.domain.model.TransferModel
import com.ra.bkuang.domain.repository.TransferRepository
import com.ra.bkuang.domain.usecase.transaksi.transfer.GetTransferById
import java.util.UUID
import javax.inject.Inject

class GetTransferByIdImpl @Inject constructor(
  private val repository: TransferRepository
): GetTransferById {
  override suspend fun invoke(uuid: UUID): TransferModel {
    return repository.findById(uuid).toModel()
  }
}