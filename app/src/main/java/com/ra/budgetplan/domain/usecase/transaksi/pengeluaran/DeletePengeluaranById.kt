package com.ra.budgetplan.domain.usecase.transaksi.pengeluaran

import java.util.UUID

interface DeletePengeluaranById {
  suspend fun invoke(uuid: UUID)
}