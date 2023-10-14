package com.ra.bkuang.domain.usecase.analisis

import com.ra.bkuang.presentation.ui.transaction.TransactionDetail
import com.ra.bkuang.presentation.ui.transaction.TransactionType
import com.ra.bkuang.util.Resource
import java.time.LocalDateTime

interface DetailAnalytics {
  suspend fun invoke(transactionType: TransactionType, fromDate: LocalDateTime, toDate: LocalDateTime): Resource<List<TransactionDetail>>
}