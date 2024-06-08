package com.ra.bkuang.features.debt.domain.usecase

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.debt.domain.model.HutangModel
import kotlinx.coroutines.flow.Flow

interface ShowAllHutangUseCase {
  operator fun invoke(): Flow<Result<List<HutangModel>>>
}