package com.ra.bkuang.features.budget.domain.usecase

import com.ra.bkuang.common.util.Result
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface DeleteBudgetByIdUseCase {
  operator fun invoke(id: UUID): Flow<Result<Boolean>>
}