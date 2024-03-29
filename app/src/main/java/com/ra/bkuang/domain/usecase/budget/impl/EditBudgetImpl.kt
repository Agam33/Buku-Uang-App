package com.ra.bkuang.domain.usecase.budget.impl

import com.ra.bkuang.domain.mapper.toEntity
import com.ra.bkuang.domain.model.BudgetModel
import com.ra.bkuang.domain.repository.BudgetRepository
import com.ra.bkuang.domain.repository.PengeluaranRepository
import com.ra.bkuang.domain.usecase.budget.EditBudget
import com.ra.bkuang.util.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters
import java.util.Calendar
import javax.inject.Inject

class EditBudgetImpl @Inject constructor(
  private val budgetRepository: BudgetRepository,
  private val pengeluaranRepository: PengeluaranRepository
): EditBudget {

  override fun invoke(budgetModel: BudgetModel): Flow<ResourceState> {
    return flow {
      emit(ResourceState.LOADING)

      val calendar = Calendar.getInstance()
      calendar.set(Calendar.YEAR, budgetModel.bulanTahun.year)
      calendar.set(Calendar.MONTH, budgetModel.bulanTahun.month.value - 1)
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

      budgetModel.pengeluaran = totalPengeluaran.toInt()

      budgetRepository.update(budgetModel.toEntity())
      emit(ResourceState.SUCCESS)
    }
  }
}