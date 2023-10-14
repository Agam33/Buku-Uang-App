package com.ra.bkuang.domain.usecase.budget.impl

import com.ra.bkuang.domain.mapper.toModel
import com.ra.bkuang.domain.model.BudgetModel
import com.ra.bkuang.domain.repository.BudgetRepository
import com.ra.bkuang.domain.usecase.budget.FindBudgetById
import java.util.UUID
import javax.inject.Inject

class FindBudgetByIdImpl @Inject constructor(
  private val budgetRepository: BudgetRepository
): FindBudgetById {
  override suspend fun invoke(id: UUID): BudgetModel {
    return budgetRepository.findById(id).toModel()
  }
}