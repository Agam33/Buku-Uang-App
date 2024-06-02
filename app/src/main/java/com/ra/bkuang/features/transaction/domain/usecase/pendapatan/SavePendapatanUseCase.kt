package com.ra.bkuang.features.transaction.domain.usecase.pendapatan

import com.ra.bkuang.features.transaction.domain.model.PendapatanModel
import com.ra.bkuang.common.util.ResourceState

interface SavePendapatanUseCase {
  suspend fun invoke(pendapatanModel: PendapatanModel): ResourceState
}