package com.ra.bkuang.features.debt.domain.usecase

import com.ra.bkuang.features.debt.data.model.PembayaranHutangModel
import com.ra.bkuang.common.util.Result
import kotlinx.coroutines.flow.Flow

interface SavePembayaranHutangUseCase {
  operator fun invoke(pembayaranHutangModel: PembayaranHutangModel): Flow<Result<Boolean>>
}