package com.ra.bkuang.features.budget.domain.usecase

import com.ra.bkuang.common.util.ResourceState
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface DeleteBudgetById {
  fun invoke(id: UUID): Flow<ResourceState>
}