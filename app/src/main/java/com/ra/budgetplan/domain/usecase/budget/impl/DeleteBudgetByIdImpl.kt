package com.ra.budgetplan.domain.usecase.budget.impl

import com.ra.budgetplan.domain.repository.BudgetRepository
import com.ra.budgetplan.domain.usecase.budget.DeleteBudgetById
import com.ra.budgetplan.util.StatusItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID
import javax.inject.Inject

class DeleteBudgetByIdImpl @Inject constructor(
  private val budgetRepository: BudgetRepository
): DeleteBudgetById {

  override fun invoke(id: UUID): Flow<StatusItem> {
    return flow {
      emit(StatusItem.LOADING)
      val budget = budgetRepository.findById(id)
      budgetRepository.delete(budget)
      emit(StatusItem.SUCCESS)
    }
  }
}