package com.ra.bkuang.features.debt.domain.usecase

import com.ra.bkuang.features.debt.domain.model.HutangModel

interface DeleteHutang {
  suspend fun invoke(hutangModel: HutangModel): Boolean
}