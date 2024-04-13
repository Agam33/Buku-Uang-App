package com.ra.bkuang.features.transaction.domain.usecase.transfer.impl

import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.transaction.data.entity.DetailTransfer
import com.ra.bkuang.features.transaction.domain.TransferRepository
import com.ra.bkuang.features.transaction.domain.usecase.transfer.GetTransferByDate
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject

class GetTransferByDateImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispather: CoroutineDispatcher,
  private val repository: TransferRepository
): GetTransferByDate {
  override suspend fun invoke(
    fromDate: LocalDateTime,
    toDate: LocalDateTime
  ): List<DetailTransfer> = withContext(ioDispather) {
    return@withContext repository.getTransferByDate(fromDate, toDate)
  }
}