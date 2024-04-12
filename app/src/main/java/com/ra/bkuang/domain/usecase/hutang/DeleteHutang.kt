package com.ra.bkuang.domain.usecase.hutang

import com.ra.bkuang.domain.model.HutangModel
import com.ra.bkuang.util.ResourceState
import kotlinx.coroutines.flow.Flow

interface DeleteHutang {
  suspend fun invoke(hutangModel: HutangModel): Boolean
}