package com.ra.bkuang.features.analytics.domain.usecase

import com.ra.bkuang.common.util.ResultState
import com.ra.bkuang.features.transaction.domain.model.TransactionDetail
import com.ra.bkuang.features.transaction.presentation.TransactionType
import java.time.LocalDateTime

interface DetailAnalyticsUseCase {
  suspend fun invoke(transactionType: TransactionType, fromDate: LocalDateTime, toDate: LocalDateTime): ResultState<List<TransactionDetail>>
}