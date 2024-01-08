package com.ra.bkuang.domain.usecase.hutang

import com.ra.bkuang.domain.model.DetailPembayaranHutangModel
import com.ra.bkuang.domain.util.ResourceState
import kotlinx.coroutines.flow.Flow

interface DeleteRecordPembayaranHutang {
  suspend fun invoke(detailPembayaranHutangModel: DetailPembayaranHutangModel): Flow<ResourceState>
}