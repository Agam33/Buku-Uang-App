package com.ra.budgetplan.domain.usecase.hutang

import com.ra.budgetplan.domain.model.HutangModel
import com.ra.budgetplan.util.StatusItem
import kotlinx.coroutines.flow.Flow

interface UpdateHutang {
  fun invoke(hutangModel: HutangModel): Flow<StatusItem>
}