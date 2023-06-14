package com.ra.budgetplan.data.repositoryimpl

import com.ra.budgetplan.data.local.BudgetLocalDataSource
import com.ra.budgetplan.domain.entity.BudgetEntity
import com.ra.budgetplan.domain.repository.BudgetRepository
import javax.inject.Inject

class BudgetRepositoryImpl @Inject constructor(
  private val localDataSource: BudgetLocalDataSource
): BudgetRepository {
  override suspend fun save(budget: BudgetEntity) {
    return localDataSource.saveBudget(budget)
  }

  override suspend fun delete(budget: BudgetEntity) {
    return localDataSource.deleteBudget(budget)
  }

  override suspend fun update(budget: BudgetEntity) {
    return localDataSource.updateBudget(budget)
  }
}