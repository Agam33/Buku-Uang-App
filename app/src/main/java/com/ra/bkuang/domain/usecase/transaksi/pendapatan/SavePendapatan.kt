package com.ra.bkuang.domain.usecase.transaksi.pendapatan

import com.ra.bkuang.domain.model.PendapatanModel
import com.ra.bkuang.domain.util.ResourceState

interface SavePendapatan {
  suspend fun invoke(pendapatanModel: PendapatanModel): ResourceState
}