package com.ra.budgetplan.domain.usecase.budget

import com.ra.budgetplan.util.StatusItem
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface DeleteBudgetById {
  fun invoke(id: UUID): Flow<StatusItem>
}