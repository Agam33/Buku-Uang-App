package com.ra.budgetplan.domain.usecase.transaksi.pengeluaran.impl

import com.ra.budgetplan.domain.repository.PengeluaranRepository
import com.ra.budgetplan.domain.usecase.transaksi.pengeluaran.GetTotalPengeluaranByDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import javax.inject.Inject

class GetTotalPengeluaranByDateImpl @Inject constructor(
  private val pengeluaranRepository: PengeluaranRepository
): GetTotalPengeluaranByDate {

  override fun invoke(fromDate: LocalDateTime, toDate: LocalDateTime): Flow<Long> {
    return flow {
      val expense = pengeluaranRepository.getTotalPengeluaranByDate(fromDate, toDate).first()
      emit(expense ?: 0)
    }
  }
}