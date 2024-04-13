package com.ra.bkuang.features.budget.domain.usecase.impl

import com.ra.bkuang.features.budget.data.mapper.toModel
import com.ra.bkuang.features.budget.domain.BudgetRepository
import com.ra.bkuang.features.budget.domain.model.BudgetModel
import com.ra.bkuang.features.budget.domain.usecase.FindBudgetById
import java.util.UUID
import javax.inject.Inject

class FindBudgetByIdImpl @Inject constructor(
  private val budgetRepository: BudgetRepository
): FindBudgetById {
  override suspend fun invoke(id: UUID): BudgetModel {
    return budgetRepository.findById(id).toModel()
  }
}