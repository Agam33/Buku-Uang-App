package com.ra.bkuang.features.transaction.domain.usecase.pengeluaran

import com.ra.bkuang.common.util.ResourceState
import java.util.UUID

interface DeletePengeluaranByIdUseCase {
  suspend fun invoke(uuid: UUID): ResourceState
}