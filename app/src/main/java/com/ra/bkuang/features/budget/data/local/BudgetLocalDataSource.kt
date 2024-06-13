package com.ra.bkuang.features.budget.data.local

import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.util.UUID

interface BudgetLocalDataSource {
  suspend fun findBudgetByDateAndKategoriId(fromDate: LocalDate, toDate: LocalDate, id: UUID): BudgetEntity
  fun findAllByDate(fromDate: LocalDate, toDate: LocalDate): Flow<List<DetailBudget>>
  suspend fun isExistByDateAndKategoriId(fromDate: LocalDate, toDate: LocalDate, id: UUID): Boolean
  suspend fun findById(id: UUID): BudgetEntity
  suspend fun save(budget: BudgetEntity)
  suspend fun delete(budget: BudgetEntity)
  suspend fun update(budget: BudgetEntity)
}