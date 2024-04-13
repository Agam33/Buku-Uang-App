package com.ra.bkuang.features.account.domain.usecase

import kotlinx.coroutines.flow.Flow

interface AkunOverallMoney {
  fun invoke(): Flow<Long>
}