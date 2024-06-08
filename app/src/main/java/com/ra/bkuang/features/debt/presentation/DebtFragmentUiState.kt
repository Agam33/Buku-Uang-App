package com.ra.bkuang.features.debt.presentation

import com.ra.bkuang.features.debt.domain.model.HutangModel

data class DebtFragmentUiState(
  val debtList: List<HutangModel> = emptyList(),
  val isSuccessfulCreate: Boolean? = null,
  val isSuccessfulUpdate: Boolean? = null,
)
