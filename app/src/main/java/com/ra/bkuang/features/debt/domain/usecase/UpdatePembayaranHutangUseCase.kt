package com.ra.bkuang.features.debt.domain.usecase

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.debt.domain.model.PembayaranHutangModel
import kotlinx.coroutines.flow.Flow

interface UpdatePembayaranHutangUseCase {
  operator fun invoke(
    newModel: PembayaranHutangModel,
    oldModel: PembayaranHutangModel
  ): Flow<Result<Boolean>>
}