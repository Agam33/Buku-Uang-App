package com.ra.bkuang.features.account.domain.usecase.impl

import com.ra.bkuang.features.account.domain.AkunRepository
import com.ra.bkuang.features.account.domain.usecase.AkunOverallMoney
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