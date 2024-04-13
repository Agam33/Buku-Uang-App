package com.ra.bkuang.features.budget.domain

import com.ra.bkuang.features.budget.data.local.DetailBudget
import com.ra.bkuang.features.budget.domain.model.BudgetModel
import java.time.LocalDate
import java.util.UUID

interface BudgetRepository {
  suspend fun findBudgetByDateAndKategoriId(fromDate: LocalDate, toDate: LocalDate, id: UUID): BudgetModel
  suspend fun findAllByDate(fromDate: LocalDate, toDate: LocalDate): List<DetailBudget>
  suspend fun isExistByDateAndKategoriId(fromDate: LocalDate, toDate: LocalDate, id: UUID): Boolean
  suspend fun findById(id: UUID): BudgetModel
  suspend fun save(budget: BudgetModel)
  suspend fun delete(budget: BudgetModel)
  suspend fun update(budget: BudgetModel)
}