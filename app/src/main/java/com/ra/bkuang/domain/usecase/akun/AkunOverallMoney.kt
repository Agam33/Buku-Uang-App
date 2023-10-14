package com.ra.bkuang.domain.usecase.akun

import kotlinx.coroutines.flow.Flow

interface AkunOverallMoney {
  fun invoke(): Flow<Long>
}