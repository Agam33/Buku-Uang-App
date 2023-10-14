package com.ra.bkuang.domain.usecase.hutang

import com.ra.bkuang.domain.model.PembayaranHutangModel
import com.ra.bkuang.util.ResourceState
import kotlinx.coroutines.flow.Flow

interface SavePembayaranHutang {
  suspend fun invoke(pembayaranHutangModel: PembayaranHutangModel): Flow<ResourceState>
}