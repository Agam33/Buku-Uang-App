package com.ra.bkuang.features.transaction.domain.usecase.pendapatan.impl

import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.transaction.domain.PendapatanRepository
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.GetTotalPendapatanByDate
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject

class GetTotalPendapatanByDateImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispather: CoroutineDispatcher,
  private val pendapatanRepository: PendapatanRepository
): GetTotalPendapatanByDate {
  override suspend fun invoke(fromDate: LocalDateTime, toDate: LocalDateTime): Long = withContext(ioDispather) {
    return@withContext pendapatanRepository.getTotalPendapatanByDate(fromDate, toDate) ?: 0
  }
}