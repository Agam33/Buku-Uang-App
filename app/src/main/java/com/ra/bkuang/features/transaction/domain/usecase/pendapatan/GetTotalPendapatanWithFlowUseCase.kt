package com.ra.bkuang.features.transaction.domain.usecase.pendapatan

import kotlinx.coroutines.flow.Flow

interface GetTotalPendapatanWithFlowUseCase {
  fun invoke(): Flow<Long>
}