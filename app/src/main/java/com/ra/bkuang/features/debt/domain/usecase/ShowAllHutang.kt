package com.ra.bkuang.features.debt.domain.usecase

import com.ra.bkuang.features.debt.domain.model.HutangModel
import com.ra.bkuang.common.util.Resource

interface ShowAllHutang {
  suspend fun invoke(): Resource<List<HutangModel>>
}