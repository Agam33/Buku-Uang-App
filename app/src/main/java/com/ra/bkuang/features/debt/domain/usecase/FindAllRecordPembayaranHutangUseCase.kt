package com.ra.bkuang.features.debt.domain.usecase

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.debt.domain.model.DetailPembayaranHutangModel
import kotlinx.coroutines.flow.Flow

interface FindAllRecordPembayaranHutangUseCase {
  operator fun invoke(id: String): Flow<Result<List<DetailPembayaranHutangModel>>>
}