package com.ra.bkuang.features.transaction.domain.usecase.pendapatan.impl

import com.ra.bkuang.features.transaction.domain.PendapatanRepository
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.GetTotalPendapatanByDateUseCase
import java.time.LocalDateTime
import javax.inject.Inject

class GetTotalPendapatanByDateUseCaseImpl @Inject constructor(
  private val pendapatanRepository: PendapatanRepository
): GetTotalPendapatanByDateUseCase {
  override suspend fun invoke(fromDate: LocalDateTime, toDate: LocalDateTime): Long  {
    return pendapatanRepository.getTotalPendapatanByDate(fromDate, toDate) ?: 0
  }
}