package com.ra.bkuang.features.transaction.domain.usecase.pendapatan.impl

import com.ra.bkuang.features.transaction.domain.PendapatanRepository
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.GetTotalPendapatanWithFlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTotalPendapatanWithFlowUseCaseImpl @Inject constructor(
  private val pendapatanRepository: PendapatanRepository
): GetTotalPendapatanWithFlowUseCase {
  override fun invoke(): Flow<Long> {
    return flow {
      pendapatanRepository.getTotalPendapatanWithFlow().collect {
        emit(it ?: 0)
      }
    }
  }
}