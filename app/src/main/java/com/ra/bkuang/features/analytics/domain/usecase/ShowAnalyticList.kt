package com.ra.bkuang.features.analytics.domain.usecase

import com.ra.bkuang.features.analytics.domain.model.AnalyticModel
import com.ra.bkuang.common.util.Resource
import com.ra.bkuang.features.transaction.presentation.TransactionType
import java.time.LocalDateTime

interface ShowAnalyticList {
  suspend fun invoke(transactionType: TransactionType, fromDate: LocalDateTime, toDate: LocalDateTime): Resource<List<AnalyticModel>>
}