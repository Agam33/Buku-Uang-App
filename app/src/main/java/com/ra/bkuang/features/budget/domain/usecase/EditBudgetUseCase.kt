package com.ra.bkuang.features.budget.domain.usecase

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.budget.data.model.BudgetModel
import kotlinx.coroutines.flow.Flow

interface EditBudgetUseCase {
  operator fun invoke(budgetModel: BudgetModel): Flow<Result<Boolean>>
}