package com.ra.bkuang.features.debt.domain.usecase

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.debt.domain.model.HutangModel
import kotlinx.coroutines.flow.Flow

interface CreateHutangUseCase {
  operator fun invoke(hutangModel: HutangModel): Flow<Result<Boolean>>
}