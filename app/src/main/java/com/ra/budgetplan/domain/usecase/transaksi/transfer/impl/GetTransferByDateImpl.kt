package com.ra.budgetplan.domain.usecase.transaksi.transfer.impl

import com.ra.budgetplan.domain.entity.DetailTransfer
import com.ra.budgetplan.domain.repository.TransferRepository
import com.ra.budgetplan.domain.usecase.transaksi.transfer.GetTransferByDate
import com.ra.budgetplan.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
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