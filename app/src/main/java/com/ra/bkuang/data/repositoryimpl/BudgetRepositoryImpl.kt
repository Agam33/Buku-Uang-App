package com.ra.bkuang.data.repositoryimpl

import com.ra.bkuang.data.local.BudgetLocalDataSource
import com.ra.bkuang.data.local.entity.BudgetEntity
import com.ra.bkuang.data.local.entity.DetailBudget
import com.ra.bkuang.domain.repository.BudgetRepository
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

class BudgetRepositoryImpl @Inject constructor(
  private val localDataSource: BudgetLocalDataSource
): BudgetRepository {
  override suspend fun findBudgetByDateAndKategoriId(
    fromDate: LocalDate,
    toDate: LocalDate,
    id: UUID
  ): BudgetEntity {
    return localDataSource.findBudgetByDateAndKategoriId(fromDate, toDate, id)
  }

  override suspend fun findAllByDate(fromDate: LocalDate, toDate: LocalDate): List<DetailBudget> {
    return localDataSource.findAllByDate(fromDate, toDate)
  }

  override suspend fun isExistByDateAndKategoriId(fromDate: LocalDate, toDate: LocalDate, id: UUID): Boolean {
    return localDataSource.isExistByDateAndKategoriId(fromDate, toDate, id)
  }

  override suspend fun findById(id: UUID): BudgetEntity {
    return localDataSource.findById(id)
  }

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