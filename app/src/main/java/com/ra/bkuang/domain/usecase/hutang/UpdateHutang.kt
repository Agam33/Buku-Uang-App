package com.ra.bkuang.domain.usecase.hutang

import com.ra.bkuang.domain.model.HutangModel
import com.ra.bkuang.domain.util.ResourceState
import kotlinx.coroutines.flow.Flow

interface UpdateHutang {
  fun invoke(hutangModel: HutangModel): Flow<ResourceState>
}