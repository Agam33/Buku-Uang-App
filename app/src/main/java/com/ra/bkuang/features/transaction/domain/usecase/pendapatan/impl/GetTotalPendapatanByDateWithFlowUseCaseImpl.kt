package com.ra.bkuang.features.transaction.domain.usecase.pendapatan.impl

import com.ra.bkuang.features.transaction.domain.PendapatanRepository
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.GetTotalPendapatanByDateWithFlowUseCase
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import javax.inject.Inject

class GetTotalPendapatanByDateWithFlowUseCaseImpl @Inject constructor(
  private val pendapatanRepository: PendapatanRepository
): GetTotalPendapatanByDateWithFlowUseCase {

  override operator fun invoke(fromDate: LocalDateTime, toDate: LocalDateTime): Flow<Long> {
    return pendapatanRepository.getTotalPendapatanByDateWithFlow(fromDate, toDate)
  }
}