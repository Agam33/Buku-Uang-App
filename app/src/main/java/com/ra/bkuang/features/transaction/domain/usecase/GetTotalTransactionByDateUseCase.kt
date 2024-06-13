package com.ra.bkuang.features.transaction.domain.usecase

import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface GetTotalTransactionByDateUseCase {
  operator fun invoke(
    fromDate: LocalDateTime,
    toDate: LocalDateTime
  ): Flow<Long>
}