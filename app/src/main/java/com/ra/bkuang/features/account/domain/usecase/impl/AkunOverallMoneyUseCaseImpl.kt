package com.ra.bkuang.features.account.domain.usecase.impl

import com.ra.bkuang.features.account.domain.repository.AkunRepository
import com.ra.bkuang.features.account.domain.usecase.AkunOverallMoneyUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AkunOverallMoneyUseCaseImpl @Inject constructor(
  private val akunRepository: AkunRepository,
): AkunOverallMoneyUseCase {

  override operator fun invoke(): Flow<Long?> {
    return akunRepository.getTotalMoney()
  }
}