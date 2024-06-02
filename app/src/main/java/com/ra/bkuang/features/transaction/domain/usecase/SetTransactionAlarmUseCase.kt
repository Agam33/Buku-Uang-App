package com.ra.bkuang.features.transaction.domain.usecase

import java.util.Calendar

interface SetTransactionAlarmUseCase {
  suspend fun invoke(calendar: Calendar): Boolean
}