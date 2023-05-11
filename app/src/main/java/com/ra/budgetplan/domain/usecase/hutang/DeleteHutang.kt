package com.ra.budgetplan.domain.usecase.hutang

import com.ra.budgetplan.domain.model.HutangModel

interface DeleteHutang {
  suspend fun invoke(hutang: HutangModel)
}