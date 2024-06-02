package com.ra.bkuang.features.account.presentation

import com.ra.bkuang.features.account.domain.model.AkunModel

data class AccountUiState(
  val isEmptyAccount: Boolean = false,
  val isSuccessfulDelete: Boolean? = null,
  val isError: Boolean = false,
  val accounts: List<AkunModel> = emptyList(),
  val totalExpense: String = "",
  val totalIncome: String = "",
  val totalMoney: String = "",
)