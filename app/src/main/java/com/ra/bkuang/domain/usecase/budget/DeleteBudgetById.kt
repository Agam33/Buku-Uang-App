package com.ra.bkuang.domain.usecase.budget

import com.ra.bkuang.util.ResourceState
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface DeleteBudgetById {
  fun invoke(id: UUID): Flow<ResourceState>
}