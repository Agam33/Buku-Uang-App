package com.ra.bkuang.domain.usecase.transaksi.transfer.impl

import com.ra.bkuang.domain.entity.DetailTransfer
import com.ra.bkuang.domain.repository.TransferRepository
import com.ra.bkuang.domain.usecase.transaksi.transfer.FindDetailTransferById
import java.util.UUID
import javax.inject.Inject

class FindDetailTransferByIdImpl @Inject constructor(
  private val transferRepository: TransferRepository
): FindDetailTransferById {

  override suspend fun invoke(id: UUID): DetailTransfer {
    return transferRepository.findDetailById(id)
  }
}