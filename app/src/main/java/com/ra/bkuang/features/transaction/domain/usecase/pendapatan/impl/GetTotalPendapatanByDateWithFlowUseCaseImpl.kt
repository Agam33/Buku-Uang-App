package com.ra.bkuang.features.transaction.domain.usecase.pendapatan.impl

import com.ra.bkuang.features.transaction.domain.PendapatanRepository
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.GetTotalPendapatanByDateWithFlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import javax.inject.Inject

class GetTotalPendapatanByDateWithFlowUseCaseImpl @Inject constructor(
  private val pendapatanRepository: PendapatanRepository
): GetTotalPendapatanByDateWithFlowUseCase {

  override fun invoke(fromDate: LocalDateTime, toDate: LocalDateTime): Flow<Long> {
    return flow {
      val income = pendapatanRepository.getTotalPendapatanByDate(fromDate, toDate) ?: 0
      emit(income)
    }
  }
}