package com.ra.bkuang.features.budget.domain.usecase.impl

import com.ra.bkuang.features.budget.data.local.DetailBudget
import com.ra.bkuang.features.budget.domain.BudgetRepository
import com.ra.bkuang.features.budget.domain.usecase.FindAllBudgetByDateUseCase
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class FindAllBudgetByDateUseCaseImpl @Inject constructor(
  private val budgetRepository: BudgetRepository
): FindAllBudgetByDateUseCase {

  override operator fun invoke(fromDate: LocalDate, toDate: LocalDate): Flow<List<DetailBudget>> {
    return budgetRepository.findAllByDate(fromDate, toDate)
  }
}