package com.ra.bkuang.features.transaction.presentation.tab.expense.uistate

import com.ra.bkuang.features.transaction.data.entity.DetailPengeluaran

data class ExpenseFragmentUiState(
  val expenseList: List<DetailPengeluaran> = emptyList(),
  val isSuccessful: Boolean? = null
)
