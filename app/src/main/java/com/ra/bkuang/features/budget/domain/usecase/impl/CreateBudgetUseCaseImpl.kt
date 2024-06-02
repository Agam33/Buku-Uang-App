package com.ra.bkuang.features.budget.domain.usecase.impl

import com.ra.bkuang.common.util.ResourceState
import com.ra.bkuang.features.budget.domain.BudgetRepository
import com.ra.bkuang.features.budget.domain.model.BudgetModel
import com.ra.bkuang.features.budget.domain.usecase.CreateBudgetUseCase
import com.ra.bkuang.features.transaction.domain.PengeluaranRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters
import java.util.Calendar
import javax.inject.Inject

class CreateBudgetUseCaseImpl @Inject constructor(
  private val budgetRepository: BudgetRepository,
  private val pengeluaranRepository: PengeluaranRepository
): CreateBudgetUseCase {

  override operator fun invoke(
    fromDate: LocalDate,
    toDate: LocalDate,
    budgetModel: BudgetModel
  ): Flow<ResourceState> {
    return flow {
      val isExist =
        budgetRepository.isExistByDateAndKategoriId(fromDate, toDate, budgetModel.idKategori)
      emit(ResourceState.LOADING)
      if (isExist) {
        emit(ResourceState.FAILED)
      } else {

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, fromDate.year)
        calendar.set(Calendar.MONTH, fromDate.month.value - 1)
        val localDateTime = LocalDateTime.ofInstant(calendar.toInstant(), ZoneId.systemDefault())

        val fromD = localDateTime.withDayOfMonth(1)
          .withHour(0)
          .withMinute(0)
        val toD = localDateTime.with(TemporalAdjusters.lastDayOfMonth())
          .withHour(23)
          .withMinute(59)

        val totalPengeluaran = pengeluaranRepository.getTotalPengeluaranByDateAndKategory(
          fromD,
          toD,
          budgetModel.idKategori
        ) ?: 0

        calendar.clear()

        budgetModel.pengeluaran += totalPengeluaran.toInt()

        budgetRepository.save(budgetModel)

        emit(ResourceState.SUCCESS)
      }
    }
  }
}