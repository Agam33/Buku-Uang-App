package com.ra.bkuang.features.transaction.domain.usecase.pendapatan

import com.ra.bkuang.features.transaction.domain.model.PendapatanModel
import java.util.UUID

interface GetPendapatanByIdUseCase {
  suspend fun invoke(uuid: UUID): PendapatanModel
}