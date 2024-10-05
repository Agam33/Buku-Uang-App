package com.ra.bkuang.features.transaction.presentation.tab.income.uistate

import com.ra.bkuang.core.data.source.local.database.entity.DetailPendapatan

data class IncomeFragmentUiState(
    val incomeList: List<DetailPendapatan> = emptyList(),
    val isSuccessful: Boolean? = null
)
