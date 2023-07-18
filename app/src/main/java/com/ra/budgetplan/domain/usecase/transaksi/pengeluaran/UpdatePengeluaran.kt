package com.ra.budgetplan.domain.usecase.transaksi.pengeluaran

import com.ra.budgetplan.domain.model.PengeluaranModel

interface UpdatePengeluaran {
  suspend fun invoke(newPengeluaranModel: PengeluaranModel, oldPengeluaranModel: PengeluaranModel)
}