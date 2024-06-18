package com.ra.bkuang.common.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.ra.bkuang.common.di.IoCoroutineScopeQualifier
import com.ra.bkuang.features.debt.alarm.DebtAlarmManager
import com.ra.bkuang.features.debt.alarm.DebtAlarmManagerManagerImpl.Companion.DEBT_ALARM_EXTRA_ID
import com.ra.bkuang.features.debt.alarm.DebtAlarmManagerManagerImpl.Companion.DEBT_ALARM_EXTRA_TITLE
import com.ra.bkuang.features.debt.domain.usecase.FindHutangByAlarmIdUseCase
import com.ra.bkuang.features.debt.domain.usecase.UpdateHutangUseCase
import com.ra.bkuang.features.debt.presentation.DebtFragment.Companion.DEBT_MODEL_ID
import com.ra.bkuang.features.transaction.alarm.TransactionAlarmManager
import com.ra.bkuang.features.transaction.alarm.TransactionAlarmManagerManagerImpl.Companion.TRANSACTION_HOUR
import com.ra.bkuang.features.transaction.alarm.TransactionAlarmManagerManagerImpl.Companion.TRANSACTION_MINUTE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver: BroadcastReceiver() {
  @Inject @IoCoroutineScopeQualifier
  lateinit var ioScope: CoroutineScope
  @Inject lateinit var updateHutangUseCase: UpdateHutangUseCase
  @Inject lateinit var findHutangByAlarmIdUseCase: FindHutangByAlarmIdUseCase
  @Inject lateinit var debtAlarmManager: DebtAlarmManager
  @Inject lateinit var transactionAlarmManager: TransactionAlarmManager

  override fun onReceive(context: Context, intent: Intent) {
    context.startForegroundService(intent)

    val alarmCategory = intent.getStringExtra(ALARM_CATEGORY) ?: ""
    when(AlarmCategory.getAlarmCategoryByString(alarmCategory)) {
      AlarmCategory.DEBT -> onDebtAlarm(context, intent)
      AlarmCategory.TRANSACTION -> onAlarmTransaction(context, intent)
    }
  }

  private fun onAlarmTransaction(context: Context, intent: Intent) {
    val minute = intent.getIntExtra(TRANSACTION_MINUTE, 0)
    val hour = intent.getIntExtra(TRANSACTION_HOUR, 0)
    transactionAlarmManager.setOnNextDay(hour, minute)

    transactionAlarmManager.showNotification(context)
  }

  private fun onDebtAlarm(ctx: Context, intent: Intent) {
    val alarmTitle = intent.getStringExtra(DEBT_ALARM_EXTRA_TITLE)
    val alarmId = intent.getIntExtra(DEBT_ALARM_EXTRA_ID, -1)
    val debtModelId = intent.getStringExtra(DEBT_MODEL_ID)

    ioScope.launch {
      val debtModel = findHutangByAlarmIdUseCase.invoke(alarmId)
      debtModel.pengingatAktif = false
      debtModel.tglPengingat = ""
      debtModel.idPengingat = Int.MAX_VALUE

      updateHutangUseCase.invoke(debtModel)
    }

    debtAlarmManager.showNotification(
      ctx,
      debtModelId ?: "",
      alarmTitle ?: ""
    )
  }

  companion object {
    const val ALARM_CATEGORY = "alarm-category"
  }
}