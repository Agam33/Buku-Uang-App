package com.ra.bkuang.features.budget.domain.usecase

import com.ra.bkuang.features.budget.data.model.BudgetModel
import java.util.UUID

interface FindBudgetByIdUseCase {
  suspend operator fun invoke(id: UUID): BudgetModel
}