package com.ra.budgetplan.domain.usecase.transaksi.pendapatan

import com.ra.budgetplan.domain.model.PendapatanModel
import com.ra.budgetplan.util.ResourceState

interface SavePendapatan {
  suspend fun invoke(pendapatanModel: PendapatanModel): ResourceState
}