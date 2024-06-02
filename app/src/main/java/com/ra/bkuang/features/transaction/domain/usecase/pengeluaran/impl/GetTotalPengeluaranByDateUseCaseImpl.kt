package com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.impl

import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.transaction.domain.PengeluaranRepository
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.GetTotalPengeluaranByDateUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject

class GetTotalPengeluaranByDateUseCaseImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispatcher: CoroutineDispatcher,
  private val pengeluaranRepository: PengeluaranRepository
): GetTotalPengeluaranByDateUseCase {
  override suspend fun invoke(fromDate: LocalDateTime, toDate: LocalDateTime): Long = withContext(ioDispatcher) {
    return@withContext pengeluaranRepository.getTotalPengeluaranByDate(fromDate, toDate) ?: 0
  }
}