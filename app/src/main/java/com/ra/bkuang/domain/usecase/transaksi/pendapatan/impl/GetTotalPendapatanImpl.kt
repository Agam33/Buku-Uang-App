package com.ra.bkuang.domain.usecase.transaksi.pendapatan.impl

import com.ra.bkuang.domain.repository.PendapatanRepository
import com.ra.bkuang.domain.usecase.transaksi.pendapatan.GetTotalPendapatan
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