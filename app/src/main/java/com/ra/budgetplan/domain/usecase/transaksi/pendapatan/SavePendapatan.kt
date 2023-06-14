package com.ra.budgetplan.domain.usecase.transaksi.pendapatan

import com.ra.budgetplan.domain.model.PendapatanModel

interface SavePendapatan {
  suspend fun invoke(pendapatanModel: PendapatanModel)
}