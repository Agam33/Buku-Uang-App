package com.ra.bkuang.features.transaction.domain.usecase.pendapatan

import java.time.LocalDateTime

interface GetTotalPendapatanByDateUseCase {
  suspend fun invoke(fromDate: LocalDateTime, toDate: LocalDateTime): Long
}