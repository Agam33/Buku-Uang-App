package com.ra.bkuang.features.transaction.presentation

import com.ra.bkuang.common.util.DateViewType
import java.time.LocalDateTime

data class TransactionUiState(
  val currDate: Pair<LocalDateTime, LocalDateTime> = LocalDateTime.now() to LocalDateTime.now(),

  val dateViewType: String = DateViewType.DAILY.name,

  val incomeText: String = "",
  val expenseText: String = "",
  val totalTransactionText: String = "",
)