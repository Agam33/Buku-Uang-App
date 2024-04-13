package com.ra.bkuang.features.budget.domain.usecase.impl

import com.ra.bkuang.features.budget.domain.BudgetRepository
import com.ra.bkuang.features.budget.domain.usecase.DeleteBudgetById
import com.ra.bkuang.common.util.ResourceState
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