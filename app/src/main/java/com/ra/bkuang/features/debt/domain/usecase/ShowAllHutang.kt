package com.ra.bkuang.features.debt.domain.usecase

import com.ra.bkuang.features.debt.domain.model.HutangModel
import com.ra.bkuang.common.util.ResultState

interface ShowAllHutang {
  suspend fun invoke(): ResultState<List<HutangModel>>
}