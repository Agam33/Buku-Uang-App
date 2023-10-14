package com.ra.bkuang.data.local.datasourceimpl

import com.ra.bkuang.data.local.BudgetLocalDataSource
import com.ra.bkuang.data.local.database.dao.BudgetDao
import com.ra.bkuang.domain.entity.BudgetEntity
import com.ra.bkuang.domain.entity.DetailBudget
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

class BudgetLocalDataSourceImpl @Inject constructor(
  private val budgetDao: BudgetDao
): BudgetLocalDataSource {
  override suspend fun isExistByDateAndKategoriId(fromDate: LocalDate, toDate: LocalDate, id: UUID): Boolean {
    return budgetDao.isExistByDateAndKategoriId(fromDate, toDate, id)
  }

  override suspend fun findBudgetByDateAndKategoriId(
    fromDate: LocalDate,
    toDate: LocalDate,
    id: UUID
  ): BudgetEntity {
    return budgetDao.findBudgetByDateAndKategoriId(fromDate, toDate, id)
  }

  override suspend fun findAllByDate(fromDate: LocalDate, toDate: LocalDate): List<DetailBudget> {
    return budgetDao.findAllByDate(fromDate, toDate)
  }

  override suspend fun findById(id: UUID): BudgetEntity {
    return budgetDao.findById(id)
  }

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