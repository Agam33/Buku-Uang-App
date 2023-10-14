package com.ra.budgetplan.domain.usecase.budget

import com.ra.budgetplan.domain.model.BudgetModel
import com.ra.budgetplan.util.ResourceState
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface CreateBudget {
  fun invoke(fromDate: LocalDate, toDate: LocalDate, budgetModel: BudgetModel): Flow<ResourceState>
}