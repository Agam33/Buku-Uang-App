package com.ra.bkuang.features.transaction.domain.usecase

import java.util.Calendar

interface SetTransactionAlarm {
  suspend fun invoke(calendar: Calendar): Boolean
}