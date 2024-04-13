package com.ra.bkuang.features.budget.domain.usecase

import com.ra.bkuang.features.budget.domain.model.BudgetModel
import com.ra.bkuang.common.util.ResourceState
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface CreateBudget {
  fun invoke(fromDate: LocalDate, toDate: LocalDate, budgetModel: BudgetModel): Flow<ResourceState>
}