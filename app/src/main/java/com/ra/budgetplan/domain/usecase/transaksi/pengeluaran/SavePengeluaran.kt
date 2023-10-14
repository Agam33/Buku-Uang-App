package com.ra.budgetplan.domain.usecase.transaksi.pengeluaran

import com.ra.budgetplan.domain.model.PengeluaranModel
import com.ra.budgetplan.util.ResourceState

interface SavePengeluaran {
  suspend fun invoke(pengeluaranModel: PengeluaranModel): ResourceState
}