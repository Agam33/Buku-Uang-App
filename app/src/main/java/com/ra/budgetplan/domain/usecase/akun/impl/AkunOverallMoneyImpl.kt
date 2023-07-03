package com.ra.budgetplan.domain.usecase.akun.impl

import com.ra.budgetplan.domain.repository.AkunRepository
import com.ra.budgetplan.domain.usecase.akun.AkunOverallMoney
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AkunOverallMoneyImpl @Inject constructor(
  private val akunRepository: AkunRepository,
): AkunOverallMoney {

  override fun invoke(): Flow<Long> {
    return flow {
      akunRepository.getTotalMoney().collect {
        emit(it ?: 0)
      }
    }
  }
}