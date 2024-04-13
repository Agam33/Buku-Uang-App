package com.ra.bkuang.features.transaction.domain.usecase.transfer.impl

import com.ra.bkuang.features.transaction.data.entity.DetailTransfer
import com.ra.bkuang.features.transaction.domain.TransferRepository
import com.ra.bkuang.features.transaction.domain.usecase.transfer.GetTransferByDate
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