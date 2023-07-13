package com.ra.budgetplan.domain.usecase.budget.impl

import com.ra.budgetplan.domain.mapper.toEntity
import com.ra.budgetplan.domain.model.BudgetModel
import com.ra.budgetplan.domain.repository.BudgetRepository
import com.ra.budgetplan.domain.repository.PengeluaranRepository
import com.ra.budgetplan.domain.usecase.budget.CreateBudget
import com.ra.budgetplan.util.StatusItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters
import java.util.Calendar
import javax.inject.Inject

class CreateBudgetImpl @Inject constructor(
  private val budgetRepository: BudgetRepository,
  private val pengeluaranRepository: PengeluaranRepository
): CreateBudget {

  override fun invoke(
    fromDate: LocalDate,
    toDate: LocalDate,
    budgetModel: BudgetModel
  ): Flow<StatusItem> {
    return flow {
      val isExist = budgetRepository.isExistByDateAndKategoriId(fromDate, toDate, budgetModel.idKategori)
      emit(StatusItem.LOADING)
      if(isExist) {
        emit(StatusItem.FAILED)
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

        budgetRepository.save(budgetModel.toEntity())

        emit(StatusItem.SUCCESS)
      }
    }
  }
}