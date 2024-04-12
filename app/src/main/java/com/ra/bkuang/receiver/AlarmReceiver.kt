package com.ra.bkuang.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.ra.bkuang.alarm.AlarmCategory
import com.ra.bkuang.alarm.DebtAlarmManager
import com.ra.bkuang.alarm.DebtAlarmManagerManagerImpl.Companion.DEBT_ALARM_EXTRA_ID
import com.ra.bkuang.alarm.DebtAlarmManagerManagerImpl.Companion.DEBT_ALARM_EXTRA_TITLE
import com.ra.bkuang.alarm.TransactionAlarmManager
import com.ra.bkuang.alarm.TransactionAlarmManagerManagerImpl.Companion.TRANSACTION_HOUR
import com.ra.bkuang.alarm.TransactionAlarmManagerManagerImpl.Companion.TRANSACTION_MINUTE
import com.ra.bkuang.di.IoCoroutineScopeQualifier
import com.ra.bkuang.domain.usecase.hutang.FindHutangByAlarmId
import com.ra.bkuang.domain.usecase.hutang.UpdateHutang
import com.ra.bkuang.presentation.ui.debt.DebtFragment.Companion.DEBT_MODEL_ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver: BroadcastReceiver() {
  @Inject @IoCoroutineScopeQualifier lateinit var ioScope: CoroutineScope
  @Inject lateinit var updateHutang: UpdateHutang
  @Inject lateinit var findHutangByAlarmId: FindHutangByAlarmId
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
      val debtModel = findHutangByAlarmId.invoke(alarmId)
      debtModel.pengingatAktif = false
      debtModel.tglPengingat = ""
      debtModel.idPengingat = Int.MAX_VALUE

      updateHutang.invoke(debtModel)
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