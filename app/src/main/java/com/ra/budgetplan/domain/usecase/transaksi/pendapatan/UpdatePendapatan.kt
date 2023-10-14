package com.ra.budgetplan.domain.usecase.transaksi.pendapatan

import com.ra.budgetplan.domain.model.PendapatanModel
import com.ra.budgetplan.util.ResourceState

interface UpdatePendapatan {
  suspend fun invoke(newPendapatanModel: PendapatanModel, oldPendapatanModel: PendapatanModel): ResourceState
}