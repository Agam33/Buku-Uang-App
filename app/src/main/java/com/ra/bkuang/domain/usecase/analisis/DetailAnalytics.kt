package com.ra.bkuang.domain.usecase.analisis

import com.ra.bkuang.util.Resource
import com.ra.bkuang.domain.model.TransactionDetail
import com.ra.bkuang.presentation.ui.transaction.TransactionType
import java.time.LocalDateTime

interface DetailAnalytics {
  suspend fun invoke(transactionType: TransactionType, fromDate: LocalDateTime, toDate: LocalDateTime): Resource<List<TransactionDetail>>
}