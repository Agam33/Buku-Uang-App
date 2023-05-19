package com.ra.budgetplan.domain.repository

import com.ra.budgetplan.domain.model.BudgetModel

interface BudgetRepository {
  suspend fun save(budget: BudgetModel)
  suspend fun delete(budget: BudgetModel)
  suspend fun update(budget: BudgetModel)
}