package com.ra.bkuang.features.transaction.domain.usecase.pengeluaran

import java.time.LocalDateTime

interface GetTotalPengeluaranByDate {
  suspend fun invoke(fromDate: LocalDateTime, toDate: LocalDateTime): Long
}