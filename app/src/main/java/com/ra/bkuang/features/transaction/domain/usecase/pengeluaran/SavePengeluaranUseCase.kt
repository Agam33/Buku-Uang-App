package com.ra.bkuang.features.transaction.domain.usecase.pengeluaran

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.transaction.domain.model.PengeluaranModel
import kotlinx.coroutines.flow.Flow

interface SavePengeluaranUseCase {
  fun invoke(pengeluaranModel: PengeluaranModel): Flow<Result<Boolean>>
}