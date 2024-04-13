package com.ra.bkuang.features.budget.domain.usecase

import com.ra.bkuang.features.budget.domain.model.BudgetModel
import java.util.UUID

interface FindBudgetById {
  suspend fun invoke(id: UUID): BudgetModel
}