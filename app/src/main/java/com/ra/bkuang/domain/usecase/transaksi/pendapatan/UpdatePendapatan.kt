package com.ra.bkuang.domain.usecase.transaksi.pendapatan

import com.ra.bkuang.domain.model.PendapatanModel
import com.ra.bkuang.util.ResourceState

interface UpdatePendapatan {
  suspend fun invoke(newPendapatanModel: PendapatanModel, oldPendapatanModel: PendapatanModel): ResourceState
}