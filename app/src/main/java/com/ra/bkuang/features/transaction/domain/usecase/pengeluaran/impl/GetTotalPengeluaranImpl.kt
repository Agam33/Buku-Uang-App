package com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.impl

import com.ra.bkuang.features.transaction.domain.PengeluaranRepository
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.GetTotalPengeluaran
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTotalPengeluaranImpl @Inject constructor(
  private val pengeluaranRepository: PengeluaranRepository
): GetTotalPengeluaran {

  override fun invoke(): Flow<Long> {
    return flow {
      pengeluaranRepository.getTotalPengeluaran().collect {
        emit(it ?: 0)
      }
    }
  }
}