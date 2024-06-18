package com.ra.bkuang.features.transaction.domain.usecase

import com.ra.bkuang.features.transaction.alarm.TransactionAlarmManager
import com.ra.bkuang.common.di.IoDispatcherQualifier
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject

class SetTransactionAlarmUseCaseImpl @Inject constructor(
  private val transactionAlarmManager: TransactionAlarmManager,
  @IoDispatcherQualifier private val ioDispatcher: CoroutineDispatcher
): SetTransactionAlarmUseCase {
  override suspend fun invoke(calendar: Calendar): Boolean =
    withContext(ioDispatcher) {
      transactionAlarmManager.setAlarm(calendar)
      return@withContext true
    }
}