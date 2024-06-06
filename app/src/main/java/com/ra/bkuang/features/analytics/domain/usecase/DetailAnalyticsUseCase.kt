package com.ra.bkuang.features.analytics.domain.usecase

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.transaction.domain.model.TransactionDetail
import com.ra.bkuang.features.transaction.presentation.TransactionType
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface DetailAnalyticsUseCase {
  operator fun invoke(
    transactionType: TransactionType,
    fromDate: LocalDateTime,
    toDate: LocalDateTime
  ): Flow<Result<List<TransactionDetail>>>
}