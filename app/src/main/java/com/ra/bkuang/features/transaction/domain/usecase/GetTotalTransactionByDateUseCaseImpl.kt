package com.ra.bkuang.features.transaction.domain.usecase

import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.transaction.domain.PendapatanRepository
import com.ra.bkuang.features.transaction.domain.PengeluaranRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.time.LocalDateTime
import javax.inject.Inject

class GetTotalTransactionByDateUseCaseImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispather: CoroutineDispatcher,
  private val pendapatanRepository: PendapatanRepository,
  private val pengeluaranRepository: PengeluaranRepository
): GetTotalTransactionByDateUseCase {

  override fun invoke(fromDate: LocalDateTime, toDate: LocalDateTime): Flow<Long> {
    return flow {
      val totalExpense = pengeluaranRepository.getTotalPengeluaranByDateWithFlow(fromDate, toDate).first() ?: 0
      val totalIncome = pendapatanRepository.getTotalPendapatanByDateWithFlow(fromDate, toDate).first() ?: 0
      emit(totalIncome - totalExpense)
    }.flowOn(ioDispather)
  }
}