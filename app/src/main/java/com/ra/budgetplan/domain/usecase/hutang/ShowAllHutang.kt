package com.ra.budgetplan.domain.usecase.hutang

import com.ra.budgetplan.domain.model.HutangModel
import com.ra.budgetplan.util.Resource

interface ShowAllHutang {
  suspend fun invoke(): Resource<List<HutangModel>>
}