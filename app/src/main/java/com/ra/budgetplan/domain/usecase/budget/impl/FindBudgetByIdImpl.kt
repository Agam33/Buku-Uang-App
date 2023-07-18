package com.ra.budgetplan.domain.usecase.budget.impl

import com.ra.budgetplan.domain.mapper.toModel
import com.ra.budgetplan.domain.model.BudgetModel
import com.ra.budgetplan.domain.repository.BudgetRepository
import com.ra.budgetplan.domain.usecase.budget.FindBudgetById
import java.util.UUID
import javax.inject.Inject

class FindBudgetByIdImpl @Inject constructor(
  private val budgetRepository: BudgetRepository
): FindBudgetById {
  override suspend fun invoke(id: UUID): BudgetModel {
    return budgetRepository.findById(id).toModel()
  }
}