package com.ra.budgetplan.data.local

import com.ra.budgetplan.domain.entity.BudgetEntity

interface BudgetLocalDataSource {
  suspend fun saveBudget(budget: BudgetEntity)
  suspend fun deleteBudget(budget: BudgetEntity)
  suspend fun updateBudget(budget: BudgetEntity)
}