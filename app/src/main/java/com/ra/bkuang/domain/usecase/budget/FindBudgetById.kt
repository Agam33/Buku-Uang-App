package com.ra.bkuang.domain.usecase.budget

import com.ra.bkuang.domain.model.BudgetModel
import java.util.UUID

interface FindBudgetById {
  suspend fun invoke(id: UUID): BudgetModel
}