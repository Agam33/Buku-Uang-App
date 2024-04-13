package com.ra.bkuang.features.debt.domain.usecase

import com.ra.bkuang.features.debt.domain.model.HutangModel
import kotlinx.coroutines.flow.Flow

interface FindHutangByIdWithFlow {
  fun invoke(id: String): Flow<HutangModel?>
}