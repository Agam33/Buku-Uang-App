package com.ra.bkuang.domain.usecase.transaksi

import com.ra.bkuang.domain.repository.PendapatanRepository
import com.ra.bkuang.domain.repository.PengeluaranRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import javax.inject.Inject

class GetTotalTransactionByDateImpl @Inject constructor(
  private val pendapatanRepository: PendapatanRepository,
  private val pengeluaranRepository: PengeluaranRepository
): GetTotalTransactionByDate {

  override fun invoke(fromDate: LocalDateTime, toDate: LocalDateTime): Flow<Long> {
    return flow {
      val totalExpense = pengeluaranRepository.getTotalPengeluaranByDate(fromDate, toDate).first() ?: 0
      val totalIncome = pendapatanRepository.getTotalPendapatanByDate(fromDate, toDate).first() ?: 0
      emit(totalIncome - totalExpense)
    }
  }
}