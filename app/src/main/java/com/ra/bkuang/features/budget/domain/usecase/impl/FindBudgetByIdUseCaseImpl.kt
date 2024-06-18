package com.ra.bkuang.features.budget.domain.usecase.impl

import com.ra.bkuang.features.budget.domain.repository.BudgetRepository
import com.ra.bkuang.features.budget.domain.model.BudgetModel
import com.ra.bkuang.features.budget.domain.usecase.FindBudgetByIdUseCase
import java.util.UUID
import javax.inject.Inject

class FindBudgetByIdUseCaseImpl @Inject constructor(
  private val budgetRepository: BudgetRepository
): FindBudgetByIdUseCase {
  override suspend operator fun invoke(id: UUID): BudgetModel {
    return budgetRepository.findById(id)
  }
}