package com.ra.budgetplan.data.repositoryimpl

import com.ra.budgetplan.data.local.BudgetLocalDataSource
import com.ra.budgetplan.domain.mapper.BudgetMapper
import com.ra.budgetplan.domain.model.BudgetModel
import com.ra.budgetplan.domain.repository.BudgetRepository
import javax.inject.Inject

class BudgetRepositoryImpl @Inject constructor(
  private val localDataSource: BudgetLocalDataSource
): BudgetRepository {
  override suspend fun save(budget: BudgetModel) {
    return localDataSource.saveBudget(BudgetMapper.budgetToEntity(budget))
  }

  override suspend fun delete(budget: BudgetModel) {
    return localDataSource.deleteBudget(BudgetMapper.budgetToEntity(budget))
  }

  override suspend fun update(budget: BudgetModel) {
    return localDataSource.updateBudget(BudgetMapper.budgetToEntity(budget))
  }
}