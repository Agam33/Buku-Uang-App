package com.ra.bkuang.features.transaction.domain.usecase.pendapatan.impl

import com.ra.bkuang.features.transaction.domain.PendapatanRepository
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.GetTotalPendapatanByDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import javax.inject.Inject

class GetTotalPendapatanByDateImpl @Inject constructor(
  private val pendapatanRepository: PendapatanRepository
): GetTotalPendapatanByDate {

  override fun invoke(fromDate: LocalDateTime, toDate: LocalDateTime): Flow<Long> {
    return flow {
      val income = pendapatanRepository.getTotalPendapatanByDate(fromDate, toDate).first() ?: 0
      emit(income)
    }
  }
}