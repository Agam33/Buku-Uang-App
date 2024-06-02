package com.ra.bkuang.features.transaction.domain.usecase.pengeluaran

import com.ra.bkuang.features.transaction.domain.model.PengeluaranModel
import com.ra.bkuang.common.util.ResourceState

interface SavePengeluaranUseCase {
  suspend fun invoke(pengeluaranModel: PengeluaranModel): ResourceState
}