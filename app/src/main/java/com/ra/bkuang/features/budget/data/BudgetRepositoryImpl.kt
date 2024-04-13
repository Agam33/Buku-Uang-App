package com.ra.bkuang.features.budget.data

import com.ra.bkuang.features.budget.data.local.BudgetLocalDataSource
import com.ra.bkuang.features.budget.data.local.DetailBudget
import com.ra.bkuang.features.budget.data.mapper.toEntity
import com.ra.bkuang.features.budget.data.mapper.toModel
import com.ra.bkuang.features.budget.domain.BudgetRepository
import com.ra.bkuang.features.budget.domain.model.BudgetModel
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
  ): BudgetModel {
    return localDataSource.findBudgetByDateAndKategoriId(fromDate, toDate, id).toModel()
  }

  override suspend fun findAllByDate(fromDate: LocalDate, toDate: LocalDate): List<DetailBudget> {
    return localDataSource.findAllByDate(fromDate, toDate)
  }

  override suspend fun isExistByDateAndKategoriId(fromDate: LocalDate, toDate: LocalDate, id: UUID): Boolean {
    return localDataSource.isExistByDateAndKategoriId(fromDate, toDate, id)
  }

  override suspend fun findById(id: UUID): BudgetModel {
    return localDataSource.findById(id).toModel()
  }

  override suspend fun save(budget: BudgetModel) {
    return localDataSource.saveBudget(budget.toEntity())
  }

  override suspend fun delete(budget: BudgetModel) {
    return localDataSource.deleteBudget(budget.toEntity())
  }

  override suspend fun update(budget: BudgetModel) {
    return localDataSource.updateBudget(budget.toEntity())
  }
}