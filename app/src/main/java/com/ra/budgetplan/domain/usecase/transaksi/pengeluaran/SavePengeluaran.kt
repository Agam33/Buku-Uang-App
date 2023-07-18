package com.ra.budgetplan.domain.usecase.transaksi.pengeluaran

import com.ra.budgetplan.domain.model.PengeluaranModel

interface SavePengeluaran {
  suspend fun invoke(pengeluaranModel: PengeluaranModel)
}