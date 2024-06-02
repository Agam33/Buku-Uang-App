package com.ra.bkuang.features.transaction.domain.usecase.pendapatan

import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface GetTotalPendapatanByDateWithFlowUseCase {
  fun invoke(fromDate: LocalDateTime, toDate: LocalDateTime): Flow<Long>
}