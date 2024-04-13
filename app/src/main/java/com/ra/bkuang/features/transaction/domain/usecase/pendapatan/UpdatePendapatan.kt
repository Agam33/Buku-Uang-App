package com.ra.bkuang.features.transaction.domain.usecase.pendapatan

import com.ra.bkuang.features.transaction.domain.model.PendapatanModel
import com.ra.bkuang.common.util.ResourceState

interface UpdatePendapatan {
  suspend fun invoke(newPendapatanModel: PendapatanModel, oldPendapatanModel: PendapatanModel): ResourceState
}