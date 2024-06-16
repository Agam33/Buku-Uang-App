package com.ra.bkuang.features.analytics.domain

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.analytics.domain.model.AnalyticModel
import com.ra.bkuang.features.transaction.domain.model.TransactionDetail
import com.ra.bkuang.features.transaction.presentation.TransactionType
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface AnalyticsRepository {
  fun showAnalyticList(transactionType: TransactionType, fromDate: LocalDateTime, toDate: LocalDateTime): Flow<Result<List<AnalyticModel>>>
  fun detailAnalytics(transactionType: TransactionType, fromDate: LocalDateTime, toDate: LocalDateTime): Flow<Result<List<TransactionDetail>>>
}