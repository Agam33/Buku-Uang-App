package com.ra.bkuang.features.transaction.domain.usecase.pengeluaran

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.transaction.domain.model.PengeluaranModel
import kotlinx.coroutines.flow.Flow

interface UpdatePengeluaranUseCase {
  fun invoke(newPengeluaranModel: PengeluaranModel, oldPengeluaranModel: PengeluaranModel): Flow<Result<Boolean>>
}