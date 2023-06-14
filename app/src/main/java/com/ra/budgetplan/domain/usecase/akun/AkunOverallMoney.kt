package com.ra.budgetplan.domain.usecase.akun

import kotlinx.coroutines.flow.Flow

interface AkunOverallMoney {
  fun invoke(): Flow<Long>
}