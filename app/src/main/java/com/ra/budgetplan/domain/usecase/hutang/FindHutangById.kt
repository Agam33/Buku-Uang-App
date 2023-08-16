package com.ra.budgetplan.domain.usecase.hutang

import com.ra.budgetplan.domain.model.HutangModel
import java.util.UUID

interface FindHutangById {
  suspend fun invoke(id: UUID): HutangModel
}