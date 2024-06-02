package com.ra.bkuang.features.transaction.domain.usecase

interface CancelTransactionAlarmUseCase {
  suspend fun invoke(): Boolean
}