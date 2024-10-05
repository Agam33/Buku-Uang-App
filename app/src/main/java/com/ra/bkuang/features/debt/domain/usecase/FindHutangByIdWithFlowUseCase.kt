package com.ra.bkuang.features.debt.domain.usecase

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.debt.data.model.HutangModel
import kotlinx.coroutines.flow.Flow

interface FindHutangByIdWithFlowUseCase {
  operator fun invoke(id: String): Flow<Result<HutangModel?>>
}