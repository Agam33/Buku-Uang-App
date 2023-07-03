package com.ra.budgetplan.domain.usecase.transaksi.pengeluaran

import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface GetTotalPengeluaranByDate {
  fun invoke(fromDate: LocalDateTime, toDate: LocalDateTime): Flow<Long>
}