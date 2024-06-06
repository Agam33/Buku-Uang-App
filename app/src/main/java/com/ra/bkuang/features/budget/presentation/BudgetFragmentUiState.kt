package com.ra.bkuang.features.budget.presentation

import com.ra.bkuang.features.budget.data.local.DetailBudget
import com.ra.bkuang.features.budget.domain.model.BudgetModel
import com.ra.bkuang.features.category.domain.model.KategoriModel

data class BudgetFragmentUiState(
  val isEmptyState: Boolean = false,
  val isSuccessfulUpdate: Boolean? = null,
  val isSuccessfulCreate: Boolean? = null,
  val isSuccessfulDelete: Boolean? = null,
  val errorMessage: String? = null,
  val listCategoryByType: List<KategoriModel> = emptyList(),
  val budgetModel: BudgetModel? = null,
  val budgetList: List<DetailBudget> = emptyList(),
)
