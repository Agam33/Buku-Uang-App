package com.ra.bkuang.domain.usecase.transaksi.pendapatan

import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface GetTotalPendapatanByDate {
  fun invoke(fromDate: LocalDateTime, toDate: LocalDateTime): Flow<Long>
}