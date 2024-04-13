package com.ra.bkuang.features.account.domain.usecase.impl

import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.account.domain.AkunRepository
import com.ra.bkuang.features.account.domain.usecase.AkunOverallMoney
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AkunOverallMoneyImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispatcher: CoroutineDispatcher,
  private val akunRepository: AkunRepository,
): AkunOverallMoney {

  override fun invoke(): Flow<Long> {
    return flow {
      akunRepository.getTotalMoney().collect {
        emit(it ?: 0)
      }
    }.flowOn(ioDispatcher)
  }
}