package com.ra.budgetplan.domain.usecase.budget

import com.ra.budgetplan.domain.model.BudgetModel
import java.util.UUID

interface FindBudgetById {
  suspend fun invoke(id: UUID): BudgetModel
}