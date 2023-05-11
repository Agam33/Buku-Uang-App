package com.ra.budgetplan.data.local.datasourceimpl

import com.ra.budgetplan.data.local.BudgetLocalDataSource
import com.ra.budgetplan.data.local.database.dao.BudgetDao
import com.ra.budgetplan.domain.entity.BudgetEntity
import javax.inject.Inject

class BudgetLocalDataSourceImpl @Inject constructor(
  private val budgetDao: BudgetDao
): BudgetLocalDataSource {
  override suspend fun saveBudget(budget: BudgetEntity) {
    return budgetDao.save(budget)
  }

  override suspend fun deleteBudget(budget: BudgetEntity) {
    return budgetDao.delete(budget)
  }

  override suspend fun updateBudget(budget: BudgetEntity) {
    return budgetDao.update(budget)
  }
}