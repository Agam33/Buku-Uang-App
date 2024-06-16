package com.ra.bkuang.features.transaction.presentation.tab.income.uistate

import com.ra.bkuang.features.transaction.data.entity.DetailPendapatan

data class IncomeFragmentUiState(
  val incomeList: List<DetailPendapatan> = emptyList(),
  val isSuccessful: Boolean? = null
)
