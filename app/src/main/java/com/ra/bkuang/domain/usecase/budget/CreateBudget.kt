package com.ra.bkuang.domain.usecase.budget

import com.ra.bkuang.domain.model.BudgetModel
import com.ra.bkuang.domain.util.ResourceState
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface CreateBudget {
  fun invoke(fromDate: LocalDate, toDate: LocalDate, budgetModel: BudgetModel): Flow<ResourceState>
}