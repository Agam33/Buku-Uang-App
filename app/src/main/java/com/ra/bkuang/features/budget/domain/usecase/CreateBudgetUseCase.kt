package com.ra.bkuang.features.budget.domain.usecase

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.budget.domain.model.BudgetModel
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface CreateBudgetUseCase {
  operator fun invoke(fromDate: LocalDate, toDate: LocalDate, budgetModel: BudgetModel): Flow<Result<Boolean>>
}