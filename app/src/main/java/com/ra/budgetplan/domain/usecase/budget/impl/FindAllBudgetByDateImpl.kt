package com.ra.budgetplan.domain.usecase.budget.impl

import com.ra.budgetplan.domain.entity.DetailBudget
import com.ra.budgetplan.domain.repository.BudgetRepository
import com.ra.budgetplan.domain.usecase.budget.FindAllBudgetByDate
import java.time.LocalDate
import javax.inject.Inject

class FindAllBudgetByDateImpl @Inject constructor(
  private val budgetRepository: BudgetRepository
): FindAllBudgetByDate {

  override suspend fun invoke(fromDate: LocalDate, toDate: LocalDate): List<DetailBudget> {
    return budgetRepository.findAllByDate(fromDate, toDate)
  }
}