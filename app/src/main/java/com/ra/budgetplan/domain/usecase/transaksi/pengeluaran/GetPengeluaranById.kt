package com.ra.budgetplan.domain.usecase.transaksi.pengeluaran

import com.ra.budgetplan.domain.model.PengeluaranModel
import java.util.UUID

interface GetPengeluaranById {
  suspend fun invoke(uuid: UUID): PengeluaranModel
}