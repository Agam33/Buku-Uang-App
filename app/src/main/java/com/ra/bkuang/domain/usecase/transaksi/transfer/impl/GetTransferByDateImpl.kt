package com.ra.bkuang.domain.usecase.transaksi.transfer.impl

import com.ra.bkuang.data.local.database.entity.DetailTransfer
import com.ra.bkuang.domain.repository.TransferRepository
import com.ra.bkuang.domain.usecase.transaksi.transfer.GetTransferByDate
import java.time.LocalDateTime
import javax.inject.Inject

class GetTransferByDateImpl @Inject constructor(
  private val repository: TransferRepository
): GetTransferByDate {
  override suspend fun invoke(fromDate: LocalDateTime, toDate: LocalDateTime):
          List<DetailTransfer> {
    return repository.getTransferByDate(fromDate, toDate)
  }
}