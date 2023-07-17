package com.ra.budgetplan.domain.usecase.analisis

import com.ra.budgetplan.domain.model.AnalyticModel
import com.ra.budgetplan.presentation.ui.transaction.TransactionType
import com.ra.budgetplan.util.Resource
import java.time.LocalDateTime

interface ShowAnalyticList {
  suspend fun invoke(transactionType: TransactionType, fromDate: LocalDateTime, toDate: LocalDateTime): Resource<List<AnalyticModel>>
}