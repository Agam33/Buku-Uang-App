package com.ra.bkuang.features.transaction.presentation.tab.income.uistate

import com.ra.bkuang.features.account.domain.model.AkunModel
import com.ra.bkuang.features.category.domain.model.KategoriModel
import com.ra.bkuang.features.transaction.domain.model.PendapatanModel

data class CreateIncomeUiState(
  val isSuccessful: Boolean? = null,

  val incomeModel: PendapatanModel? = null,

  val accountList: List<AkunModel> = emptyList(),
  val categoryList: List<KategoriModel> = emptyList(),
)
