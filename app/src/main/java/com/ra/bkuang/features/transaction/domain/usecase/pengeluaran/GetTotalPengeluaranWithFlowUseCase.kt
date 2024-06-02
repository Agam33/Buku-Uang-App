package com.ra.bkuang.features.transaction.domain.usecase.pengeluaran

import kotlinx.coroutines.flow.Flow

interface GetTotalPengeluaranWithFlowUseCase {
  fun invoke(): Flow<Long>
}