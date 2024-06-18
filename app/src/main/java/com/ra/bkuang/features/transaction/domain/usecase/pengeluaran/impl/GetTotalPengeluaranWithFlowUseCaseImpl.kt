package com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.impl

import com.ra.bkuang.features.transaction.domain.repository.PengeluaranRepository
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.GetTotalPengeluaranWithFlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTotalPengeluaranWithFlowUseCaseImpl @Inject constructor(
  private val pengeluaranRepository: PengeluaranRepository
): GetTotalPengeluaranWithFlowUseCase {

  override fun invoke(): Flow<Long> {
    return flow {
      pengeluaranRepository.getTotalPengeluaranWithFlow().collect {
        emit(it ?: 0)
      }
    }
  }
}