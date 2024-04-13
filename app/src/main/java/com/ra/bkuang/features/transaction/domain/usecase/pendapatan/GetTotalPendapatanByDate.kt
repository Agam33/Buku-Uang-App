package com.ra.bkuang.features.transaction.domain.usecase.pendapatan

import java.time.LocalDateTime

interface GetTotalPendapatanByDate {
  suspend fun invoke(fromDate: LocalDateTime, toDate: LocalDateTime): Long
}