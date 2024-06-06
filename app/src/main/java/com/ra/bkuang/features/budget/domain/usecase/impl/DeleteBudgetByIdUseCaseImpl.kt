package com.ra.bkuang.features.budget.domain.usecase.impl

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.budget.domain.BudgetRepository
import com.ra.bkuang.features.budget.domain.usecase.DeleteBudgetByIdUseCase
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class DeleteBudgetByIdUseCaseImpl @Inject constructor(
  private val budgetRepository: BudgetRepository
): DeleteBudgetByIdUseCase {

  override operator fun invoke(id: UUID): Flow<Result<Boolean>> {
    return budgetRepository.deleteById(id)
  }
}