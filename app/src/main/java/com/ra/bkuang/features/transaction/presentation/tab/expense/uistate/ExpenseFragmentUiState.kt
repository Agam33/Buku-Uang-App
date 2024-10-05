package com.ra.bkuang.features.transaction.presentation.tab.expense.uistate

import com.ra.bkuang.core.data.source.local.database.entity.DetailPengeluaran

data class ExpenseFragmentUiState(
    val expenseList: List<DetailPengeluaran> = emptyList(),
    val isSuccessful: Boolean? = null
)
