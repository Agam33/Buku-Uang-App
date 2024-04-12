package com.ra.bkuang.domain.usecase.transaksi

import com.ra.bkuang.alarm.TransactionAlarmManager
import com.ra.bkuang.di.IoDispatcherQualifier
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CancelAlarmTransactionImpl @Inject constructor(
  private val transactionAlarmManager: TransactionAlarmManager,
  @IoDispatcherQualifier private val ioDispatcher: CoroutineDispatcher
): CancelTransactionAlarm {
  override suspend fun invoke(): Boolean = withContext(ioDispatcher) {
    transactionAlarmManager.cancelAlarm()
    return@withContext true
  }
}