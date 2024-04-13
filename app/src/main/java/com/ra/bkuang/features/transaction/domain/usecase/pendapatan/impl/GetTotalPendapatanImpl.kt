package com.ra.bkuang.features.transaction.domain.usecase.pendapatan.impl

import com.ra.bkuang.features.transaction.domain.PendapatanRepository
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.GetTotalPendapatan
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTotalPendapatanImpl @Inject constructor(
  private val pendapatanRepository: PendapatanRepository
): GetTotalPendapatan {
  override fun invoke(): Flow<Long> {
    return flow {
      pendapatanRepository.getTotalPendapatan().collect {
        emit(it ?: 0)
      }
    }
  }
}