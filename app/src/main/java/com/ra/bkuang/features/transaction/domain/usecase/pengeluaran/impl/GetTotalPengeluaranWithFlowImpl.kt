package com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.impl

import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.transaction.domain.PengeluaranRepository
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.GetTotalPengeluaranWithFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetTotalPengeluaranWithFlowImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispather: CoroutineDispatcher,
  private val pengeluaranRepository: PengeluaranRepository
): GetTotalPengeluaranWithFlow {

  override fun invoke(): Flow<Long> {
    return flow {
      pengeluaranRepository.getTotalPengeluaranWithFlow().collect {
        emit(it ?: 0)
      }
    }.flowOn(ioDispather)
  }
}