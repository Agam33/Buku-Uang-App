package com.ra.bkuang.features.budget.domain

import com.ra.bkuang.features.budget.data.local.BudgetEntity
import com.ra.bkuang.features.budget.data.local.DetailBudget
import java.time.LocalDate
import java.util.UUID

interface BudgetRepository {
  suspend fun findBudgetByDateAndKategoriId(fromDate: LocalDate, toDate: LocalDate, id: UUID): BudgetEntity
  suspend fun findAllByDate(fromDate: LocalDate, toDate: LocalDate): List<DetailBudget>
  suspend fun isExistByDateAndKategoriId(fromDate: LocalDate, toDate: LocalDate, id: UUID): Boolean
  suspend fun findById(id: UUID): BudgetEntity
  suspend fun save(budget: BudgetEntity)
  suspend fun delete(budget: BudgetEntity)
  suspend fun update(budget: BudgetEntity)
}