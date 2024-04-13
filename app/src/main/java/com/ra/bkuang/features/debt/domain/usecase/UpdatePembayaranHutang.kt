package com.ra.bkuang.features.debt.domain.usecase

import com.ra.bkuang.features.debt.domain.model.PembayaranHutangModel
import com.ra.bkuang.common.util.ResourceState
import kotlinx.coroutines.flow.Flow

interface UpdatePembayaranHutang {
  suspend fun invoke(
    newModel: PembayaranHutangModel,
    oldModel: PembayaranHutangModel
  ): Flow<ResourceState>
}