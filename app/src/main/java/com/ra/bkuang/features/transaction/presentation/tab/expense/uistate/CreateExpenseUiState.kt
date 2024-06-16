package com.ra.bkuang.features.transaction.presentation.tab.expense.uistate

import com.ra.bkuang.features.account.domain.model.AkunModel
import com.ra.bkuang.features.category.domain.model.KategoriModel
import com.ra.bkuang.features.transaction.domain.model.PengeluaranModel

data class CreateExpenseUiState(
  val isSuccessful: Boolean? = null,

  val isSave: Boolean = false,

  val showSaveAlert: Boolean = false,

  val expenseModel: PengeluaranModel? = null,

  val accountList: List<AkunModel> = emptyList(),
  val categoryList: List<KategoriModel> = emptyList(),
)
