package com.ra.bkuang.features.transaction.domain.usecase.pendapatan.impl

import com.ra.bkuang.features.transaction.domain.PendapatanRepository
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.GetTotalPendapatanWithFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTotalPendapatanWithFlowImpl @Inject constructor(
  private val pendapatanRepository: PendapatanRepository
): GetTotalPendapatanWithFlow {
  override fun invoke(): Flow<Long> {
    return flow {
      pendapatanRepository.getTotalPendapatanWithFlow().collect {
        emit(it ?: 0)
      }
    }
  }
}