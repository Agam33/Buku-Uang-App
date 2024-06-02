package com.ra.bkuang.features.transaction.domain.usecase.pendapatan

import com.ra.bkuang.common.util.ResourceState
import java.util.UUID

interface DeletePendapatanByIdUseCase {
  suspend fun invoke(uuid: UUID): ResourceState
}