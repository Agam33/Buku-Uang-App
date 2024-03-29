package com.ra.bkuang.domain.usecase.budget.impl

import com.ra.bkuang.domain.repository.BudgetRepository
import com.ra.bkuang.domain.usecase.budget.DeleteBudgetById
import com.ra.bkuang.util.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID
import javax.inject.Inject

class DeleteBudgetByIdImpl @Inject constructor(
  private val budgetRepository: BudgetRepository
): DeleteBudgetById {

  override fun invoke(id: UUID): Flow<ResourceState> {
    return flow {
      emit(ResourceState.LOADING)
      val budget = budgetRepository.findById(id)
      budgetRepository.delete(budget)
      emit(ResourceState.SUCCESS)
    }
  }
}