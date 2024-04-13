package com.ra.bkuang.features.debt.domain.usecase

import com.ra.bkuang.features.debt.domain.model.HutangModel

interface FindHutangById {
  suspend fun invoke(id: String): HutangModel
}