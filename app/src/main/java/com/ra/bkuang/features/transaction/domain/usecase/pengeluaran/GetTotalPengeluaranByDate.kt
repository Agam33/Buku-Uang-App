package com.ra.bkuang.features.transaction.domain.usecase.pengeluaran

import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface GetTotalPengeluaranByDate {
  fun invoke(fromDate: LocalDateTime, toDate: LocalDateTime): Flow<Long>
}