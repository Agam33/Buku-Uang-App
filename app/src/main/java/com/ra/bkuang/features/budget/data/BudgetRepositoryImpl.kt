package com.ra.bkuang.features.budget.data

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.budget.data.local.BudgetLocalDataSource
import com.ra.bkuang.features.budget.data.local.DetailBudget
import com.ra.bkuang.features.budget.data.mapper.toEntity
import com.ra.bkuang.features.budget.data.mapper.toModel
import com.ra.bkuang.features.budget.domain.repository.BudgetRepository
import com.ra.bkuang.features.budget.domain.model.BudgetModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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

  override fun findAllByDate(fromDate: LocalDate, toDate: LocalDate): Flow<List<DetailBudget>> {
    return localDataSource.findAllByDate(fromDate, toDate)
  }

  override suspend fun isExistByDateAndKategoriId(fromDate: LocalDate, toDate: LocalDate, id: UUID): Boolean {
    return localDataSource.isExistByDateAndKategoriId(fromDate, toDate, id)
  }

  override suspend fun findById(id: UUID): BudgetModel {
    return localDataSource.findById(id).toModel()
  }

  override suspend fun save(budget: BudgetModel) {
    return localDataSource.save(budget.toEntity())
  }

  override fun deleteById(id: UUID): Flow<Result<Boolean>> {
    return flow {
      try {
        val budget = localDataSource.findById(id)
        localDataSource.delete(budget)
        emit(Result.Success(true))
      } catch (e: Exception) {
        emit(Result.Error(e.message.toString()))
      }
    }
  }

  override suspend fun update(budget: BudgetModel) {
    return localDataSource.update(budget.toEntity())
  }
}