package com.ra.budgetplan.domain.usecase.budget

import com.ra.budgetplan.domain.model.BudgetModel
import com.ra.budgetplan.util.ResourceState
import kotlinx.coroutines.flow.Flow

interface EditBudget {
  fun invoke(budgetModel: BudgetModel): Flow<ResourceState>
}