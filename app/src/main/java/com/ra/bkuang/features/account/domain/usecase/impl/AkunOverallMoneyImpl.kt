package com.ra.bkuang.features.account.domain.usecase.impl

import com.ra.bkuang.features.account.domain.AkunRepository
import com.ra.bkuang.features.account.domain.usecase.AkunOverallMoney
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AkunOverallMoneyImpl @Inject constructor(
  private val akunRepository: AkunRepository,
): AkunOverallMoney {

  override operator fun invoke(): Flow<Long?> {
    return akunRepository.getTotalMoney()
  }
}