package com.ra.budgetplan.domain.usecase.cicilan

import com.ra.budgetplan.domain.model.CicilanModel
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface FindCicilanById {
  fun invoke(id: UUID): Flow<CicilanModel>
}