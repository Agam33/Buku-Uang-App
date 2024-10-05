package com.ra.bkuang.features.debt.domain.usecase

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.debt.data.model.DetailPembayaranHutangModel
import kotlinx.coroutines.flow.Flow

interface DeleteRecordPembayaranHutangUseCase {
  operator fun invoke(detailPembayaranHutangModel: DetailPembayaranHutangModel): Flow<Result<Boolean>>
}