package com.ra.budgetplan.domain.usecase.hutang

import com.ra.budgetplan.domain.model.PembayaranHutangModel
import com.ra.budgetplan.util.ResourceState
import kotlinx.coroutines.flow.Flow

interface UpdatePembayaranHutang {
  suspend fun invoke(
    newModel: PembayaranHutangModel,
    oldModel: PembayaranHutangModel
  ): Flow<ResourceState>
}