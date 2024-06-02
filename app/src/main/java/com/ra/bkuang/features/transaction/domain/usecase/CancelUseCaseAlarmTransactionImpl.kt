package com.ra.bkuang.features.transaction.domain.usecase

import com.ra.bkuang.features.transaction.alarm.TransactionAlarmManager
import javax.inject.Inject

class CancelUseCaseAlarmTransactionImpl @Inject constructor(
  private val transactionAlarmManager: TransactionAlarmManager,
): CancelTransactionAlarmUseCase {
  override suspend fun invoke(): Boolean {
    transactionAlarmManager.cancelAlarm()
    return true
  }
}