package com.ra.bkuang.domain.usecase.transaksi.pengeluaran.impl

import com.ra.bkuang.domain.repository.PengeluaranRepository
import com.ra.bkuang.domain.usecase.transaksi.pengeluaran.GetTotalPengeluaran
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