package com.ra.bkuang.features.transaction.domain.usecase.pengeluaran

import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface GetTotalPengeluaranByDateWithFlowUseCase {
  fun invoke(fromDate: LocalDateTime, toDate: LocalDateTime): Flow<Long>
}