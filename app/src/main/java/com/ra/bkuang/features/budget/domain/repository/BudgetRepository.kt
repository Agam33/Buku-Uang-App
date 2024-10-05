package com.ra.bkuang.features.budget.domain.repository

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.core.data.source.local.database.entity.DetailBudget
import com.ra.bkuang.features.budget.data.model.BudgetModel
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.util.UUID

interface BudgetRepository {
  suspend fun findBudgetByDateAndKategoriId(fromDate: LocalDate, toDate: LocalDate, id: UUID): BudgetModel
  fun findAllByDate(fromDate: LocalDate, toDate: LocalDate): Flow<List<DetailBudget>>
  suspend fun isExistByDateAndKategoriId(fromDate: LocalDate, toDate: LocalDate, id: UUID): Boolean
  suspend fun findById(id: UUID): BudgetModel
  suspend fun save(budget: BudgetModel)
  fun deleteById(id: UUID): Flow<Result<Boolean>>
  suspend fun update(budget: BudgetModel)
}