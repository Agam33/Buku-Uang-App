package com.ra.bkuang.domain.usecase.transaksi

import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface GetTotalTransactionByDate {
  fun invoke(
    fromDate: LocalDateTime,
    toDate: LocalDateTime
  ): Flow<Long>
}