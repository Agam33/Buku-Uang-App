package com.ra.bkuang.features.budget.domain.usecase

import com.ra.bkuang.features.budget.domain.model.BudgetModel
import com.ra.bkuang.common.util.ResourceState
import kotlinx.coroutines.flow.Flow

interface EditBudget {
  fun invoke(budgetModel: BudgetModel): Flow<ResourceState>
}