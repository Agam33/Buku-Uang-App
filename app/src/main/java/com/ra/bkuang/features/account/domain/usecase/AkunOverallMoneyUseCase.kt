package com.ra.bkuang.features.account.domain.usecase

import kotlinx.coroutines.flow.Flow

interface AkunOverallMoneyUseCase {
  operator fun invoke(): Flow<Long?>
}