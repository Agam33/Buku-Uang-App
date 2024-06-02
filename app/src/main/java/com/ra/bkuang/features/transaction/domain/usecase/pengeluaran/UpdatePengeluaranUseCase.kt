package com.ra.bkuang.features.transaction.domain.usecase.pengeluaran

import com.ra.bkuang.features.transaction.domain.model.PengeluaranModel
import com.ra.bkuang.common.util.ResourceState

interface UpdatePengeluaranUseCase {
  suspend fun invoke(newPengeluaranModel: PengeluaranModel, oldPengeluaranModel: PengeluaranModel): ResourceState
}