package com.ra.bkuang.features.budget.domain.usecase.impl

import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.budget.data.local.DetailBudget
import com.ra.bkuang.features.budget.domain.BudgetRepository
import com.ra.bkuang.features.budget.domain.usecase.FindAllBudgetByDate
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

class FindAllBudgetByDateImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispatcher: CoroutineDispatcher,
  private val budgetRepository: BudgetRepository
): FindAllBudgetByDate {

  override suspend fun invoke(fromDate: LocalDate, toDate: LocalDate): List<DetailBudget> = withContext(ioDispatcher) {
    return@withContext budgetRepository.findAllByDate(fromDate, toDate)
  }
}