package com.ra.budgetplan.domain.repository

import com.ra.budgetplan.domain.entity.BudgetEntity

interface BudgetRepository {
  suspend fun save(budget: BudgetEntity)
  suspend fun delete(budget: BudgetEntity)
  suspend fun update(budget: BudgetEntity)
}