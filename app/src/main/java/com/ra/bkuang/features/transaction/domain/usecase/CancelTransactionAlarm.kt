package com.ra.bkuang.features.transaction.domain.usecase

interface CancelTransactionAlarm {
  suspend fun invoke(): Boolean
}