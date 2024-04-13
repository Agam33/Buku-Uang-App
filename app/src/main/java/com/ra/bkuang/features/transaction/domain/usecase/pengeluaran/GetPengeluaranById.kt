package com.ra.bkuang.features.transaction.domain.usecase.pengeluaran

import com.ra.bkuang.features.transaction.domain.model.PengeluaranModel
import java.util.UUID

interface GetPengeluaranById {
  suspend fun invoke(uuid: UUID): PengeluaranModel
}