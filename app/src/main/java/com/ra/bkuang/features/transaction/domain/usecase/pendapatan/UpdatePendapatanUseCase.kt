package com.ra.bkuang.features.transaction.domain.usecase.pendapatan

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.transaction.domain.model.PendapatanModel
import kotlinx.coroutines.flow.Flow

interface UpdatePendapatanUseCase {
  operator fun invoke(newIncomeModel: PendapatanModel, oldIncomeModel: PendapatanModel): Flow<Result<Boolean>>
}