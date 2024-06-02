package com.ra.bkuang.features.transaction.domain.usecase

import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface GetTotalTransactionByDateUseCase {
  fun invoke(
    fromDate: LocalDateTime,
    toDate: LocalDateTime
  ): Flow<Long>
}