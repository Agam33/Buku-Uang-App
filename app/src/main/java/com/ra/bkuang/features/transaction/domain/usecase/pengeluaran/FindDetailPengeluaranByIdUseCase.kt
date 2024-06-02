package com.ra.bkuang.features.transaction.domain.usecase.pengeluaran

import com.ra.bkuang.features.transaction.data.entity.DetailPengeluaran
import java.util.UUID

interface FindDetailPengeluaranByIdUseCase {
  suspend fun invoke(id: UUID): DetailPengeluaran
}