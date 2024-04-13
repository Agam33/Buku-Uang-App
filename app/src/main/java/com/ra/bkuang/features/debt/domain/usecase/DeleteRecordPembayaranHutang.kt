package com.ra.bkuang.features.debt.domain.usecase

import com.ra.bkuang.features.debt.domain.model.DetailPembayaranHutangModel
import com.ra.bkuang.common.util.ResourceState
import kotlinx.coroutines.flow.Flow

interface DeleteRecordPembayaranHutang {
  suspend fun invoke(detailPembayaranHutangModel: DetailPembayaranHutangModel): Flow<ResourceState>
}