package com.ra.bkuang.domain.usecase.hutang

import com.ra.bkuang.domain.model.PembayaranHutangModel
import com.ra.bkuang.domain.util.ResourceState
import kotlinx.coroutines.flow.Flow

interface UpdatePembayaranHutang {
  suspend fun invoke(
    newModel: PembayaranHutangModel,
    oldModel: PembayaranHutangModel
  ): Flow<ResourceState>
}