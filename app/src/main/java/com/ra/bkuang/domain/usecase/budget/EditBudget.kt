package com.ra.bkuang.domain.usecase.budget

import com.ra.bkuang.domain.model.BudgetModel
import com.ra.bkuang.domain.util.ResourceState
import kotlinx.coroutines.flow.Flow

interface EditBudget {
  fun invoke(budgetModel: BudgetModel): Flow<ResourceState>
}