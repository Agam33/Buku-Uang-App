package com.ra.budgetplan.domain.usecase.hutang

import com.ra.budgetplan.domain.model.HutangModel
import com.ra.budgetplan.util.ResourceState
import kotlinx.coroutines.flow.Flow

interface DeleteHutang {
  suspend fun invoke(hutangModel: HutangModel): Flow<ResourceState>
}