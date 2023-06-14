package com.ra.budgetplan.domain.usecase.transaksi.pengeluaran.impl

import com.ra.budgetplan.domain.repository.PengeluaranRepository
import com.ra.budgetplan.domain.usecase.transaksi.pengeluaran.GetTotalPengeluaran
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
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