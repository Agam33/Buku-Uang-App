package com.ra.bkuang.features.budget.domain.usecase.impl

import com.ra.bkuang.common.util.ResourceState
import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.budget.domain.BudgetRepository
import com.ra.bkuang.features.budget.domain.model.BudgetModel
import com.ra.bkuang.features.budget.domain.usecase.EditBudget
import com.ra.bkuang.features.transaction.domain.PengeluaranRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters
import java.util.Calendar
import javax.inject.Inject

class EditBudgetImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispatcher: CoroutineDispatcher,
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

      budgetRepository.update(budgetModel)
      emit(ResourceState.SUCCESS)
    }.flowOn(ioDispatcher)
  }
}