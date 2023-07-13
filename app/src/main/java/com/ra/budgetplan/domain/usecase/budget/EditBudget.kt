package com.ra.budgetplan.domain.usecase.budget

import com.ra.budgetplan.domain.model.BudgetModel
import com.ra.budgetplan.util.StatusItem
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface EditBudget {
  fun invoke(budgetModel: BudgetModel): Flow<StatusItem>
}