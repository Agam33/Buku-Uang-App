package com.ra.bkuang.data.local

import com.ra.bkuang.data.local.entity.BudgetEntity
import com.ra.bkuang.data.local.entity.DetailBudget
import java.time.LocalDate
import java.util.UUID

interface BudgetLocalDataSource {
  suspend fun findBudgetByDateAndKategoriId(fromDate: LocalDate, toDate: LocalDate, id: UUID): BudgetEntity
  suspend fun findAllByDate(fromDate: LocalDate, toDate: LocalDate): List<DetailBudget>
  suspend fun isExistByDateAndKategoriId(fromDate: LocalDate, toDate: LocalDate, id: UUID): Boolean
  suspend fun findById(id: UUID): BudgetEntity
  suspend fun saveBudget(budget: BudgetEntity)
  suspend fun deleteBudget(budget: BudgetEntity)
  suspend fun updateBudget(budget: BudgetEntity)
}