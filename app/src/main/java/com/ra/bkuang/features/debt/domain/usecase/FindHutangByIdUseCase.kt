package com.ra.bkuang.features.debt.domain.usecase

import com.ra.bkuang.features.debt.data.model.HutangModel

interface FindHutangByIdUseCase {
  suspend fun invoke(id: String): HutangModel
}