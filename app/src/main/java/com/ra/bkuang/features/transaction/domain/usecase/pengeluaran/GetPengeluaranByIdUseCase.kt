package com.ra.bkuang.features.transaction.domain.usecase.pengeluaran

import com.ra.bkuang.features.transaction.domain.model.PengeluaranModel
import java.util.UUID

interface GetPengeluaranByIdUseCase {
  suspend fun invoke(uuid: UUID): PengeluaranModel
}