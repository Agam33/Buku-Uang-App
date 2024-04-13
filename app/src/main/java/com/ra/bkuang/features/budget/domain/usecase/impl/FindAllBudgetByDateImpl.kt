package com.ra.bkuang.features.budget.domain.usecase.impl

import com.ra.bkuang.features.budget.data.local.DetailBudget
import com.ra.bkuang.features.budget.domain.BudgetRepository
import com.ra.bkuang.features.budget.domain.usecase.FindAllBudgetByDate
import java.time.LocalDate
import javax.inject.Inject

class FindAllBudgetByDateImpl @Inject constructor(
  private val budgetRepository: BudgetRepository
): FindAllBudgetByDate {

  override suspend fun invoke(fromDate: LocalDate, toDate: LocalDate): List<DetailBudget> {
    return budgetRepository.findAllByDate(fromDate, toDate)
  }
}