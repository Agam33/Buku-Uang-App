package com.ra.budgetplan.domain.usecase.hutang

import com.ra.budgetplan.domain.model.HutangModel
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface FindHutangByIdWithFlow {
  fun invoke(id: UUID): Flow<HutangModel>
}