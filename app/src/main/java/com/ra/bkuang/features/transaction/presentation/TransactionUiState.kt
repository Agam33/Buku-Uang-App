package com.ra.bkuang.features.transaction.presentation

import com.ra.bkuang.common.util.DateViewType
import com.ra.bkuang.common.util.getDateViewType
import com.ra.bkuang.features.transaction.presentation.tab.expense.uistate.ExpenseFragmentUiState
import com.ra.bkuang.features.transaction.presentation.tab.income.uistate.IncomeFragmentUiState
import com.ra.bkuang.features.transaction.presentation.tab.transfer.uistate.TransferFragmentUiState
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters
import java.util.Calendar

data class TransactionUiState(
  val transactionCurrDate: LocalDate = LocalDate.now(),
  val currDate: Pair<LocalDateTime, LocalDateTime> = LocalDateTime.now() to LocalDateTime.now(),

  val dateViewType: String = DateViewType.DAILY.name,

  val incomeText: String = "",
  val expenseText: String = "",
  val totalTransactionText: String = "",
  val currDateText: String = "",

  // TAB
  val incomeUiState: IncomeFragmentUiState = IncomeFragmentUiState(),
  val expenseUiState: ExpenseFragmentUiState = ExpenseFragmentUiState(),
  val transferFragmentUiState: TransferFragmentUiState = TransferFragmentUiState()
) {
  fun dateByViewType(localDate: LocalDate, viewType: String): Pair<LocalDateTime, LocalDateTime> {
    return when(getDateViewType(viewType)) {
      DateViewType.MONTHLY -> {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, localDate.year)
        calendar.set(Calendar.MONTH,localDate.month.value - 1)
        val localDateTime = LocalDateTime.ofInstant(calendar.toInstant(), ZoneId.systemDefault())

        val fromDate = localDateTime.withDayOfMonth(1)
          .withHour(0)
          .withMinute(0)
        val toDate = localDateTime.with(TemporalAdjusters.lastDayOfMonth())
          .withHour(23)
          .withMinute(59)

        fromDate to toDate
      }
      DateViewType.DAILY -> {
        val fromDate = localDate.atStartOfDay()
        val toDate = localDate.atTime(LocalTime.MAX)

        fromDate to toDate
      }
    }
  }
}