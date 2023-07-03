package com.ra.budgetplan.domain.usecase.transaksi.pendapatan

import com.ra.budgetplan.domain.model.PendapatanModel

interface UpdatePendapatan {
  suspend fun invoke(newPendapatanModel: PendapatanModel, oldPendapatanModel: PendapatanModel)
}