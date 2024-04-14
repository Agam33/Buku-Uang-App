package com.ra.bkuang.features.budget.domain.usecase.impl

import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.budget.domain.BudgetRepository
import com.ra.bkuang.features.budget.domain.model.BudgetModel
import com.ra.bkuang.features.budget.domain.usecase.FindBudgetById
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class FindBudgetByIdImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispatcher: CoroutineDispatcher,
  private val budgetRepository: BudgetRepository
): FindBudgetById {
  override suspend fun invoke(id: UUID): BudgetModel = withContext(ioDispatcher) {
    return@withContext budgetRepository.findById(id)
  }
}