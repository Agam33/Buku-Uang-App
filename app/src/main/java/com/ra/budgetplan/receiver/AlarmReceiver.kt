package com.ra.budgetplan.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import com.ra.budgetplan.R
import com.ra.budgetplan.data.local.HutangLocalDataSource
import com.ra.budgetplan.domain.mapper.toEntity
import com.ra.budgetplan.domain.mapper.toModel
import com.ra.budgetplan.presentation.ui.debt.DebtFragment
import com.ra.budgetplan.presentation.ui.debt.DebtFragment.Companion.DEBT_MODEL_ID
import com.ra.budgetplan.presentation.ui.debt.DetailDebtActivity
import com.ra.budgetplan.util.Constants.ALARM_RECEIVER_NOTIFICATION_CHANNEL_ID
import com.ra.budgetplan.util.Constants.ALARM_RECEIVER_NOTIFICATION_CHANNEL_NAME
import com.ra.budgetplan.util.Constants.ALARM_RECEIVER_NOTIFICATION_ID
import com.ra.budgetplan.util.Constants.coroutineIOThread
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver: BroadcastReceiver() {

  @Inject
  lateinit var localDataSource: HutangLocalDataSource

  override fun onReceive(context: Context, intent: Intent) {
    val alarmTitle = intent.getStringExtra(DebtFragment.DEBT_ALARM_EXTRA_TITLE)
    val alarmId = intent.getIntExtra(DebtFragment.DEBT_ALARM_EXTRA_ID, -1)
    val debtModelId = intent.getStringExtra(DEBT_MODEL_ID)

    coroutineIOThread {
      val debtModel = localDataSource.findByAlarmId(alarmId).toModel()
      debtModel.pengingatAktif = false
      debtModel.tglPengingat = ""
      debtModel.idPengingat = Int.MAX_VALUE

      localDataSource.update(debtModel.toEntity())
    }

    showNotification(
      context,
      debtModelId ?: "",
      alarmTitle ?: ""
    )
  }

  private fun showNotification(
    context: Context,
    debtModelId: String,
    title: String,
  ) {
    val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

    val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    val channel = NotificationChannel(
      ALARM_RECEIVER_NOTIFICATION_CHANNEL_ID,
      ALARM_RECEIVER_NOTIFICATION_CHANNEL_NAME,
      NotificationManager.IMPORTANCE_DEFAULT
    )

    notificationManagerCompat.createNotificationChannel(channel)

    val intent = Intent(context, DetailDebtActivity::class.java).apply {
      flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
      putExtra(DEBT_MODEL_ID, debtModelId)
    }

    val pendingIntent = PendingIntent.getActivity(
      context, 0,
      intent,
      PendingIntent.FLAG_MUTABLE
    )

    val alarmNotification = NotificationCompat.Builder(context, ALARM_RECEIVER_NOTIFICATION_CHANNEL_ID)
      .setContentTitle(String.format(context.getString(R.string.msg_title_debt_notification), title))
      .setSmallIcon(R.drawable.baseline_account_balance_wallet_24)
      .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
      .setGroupSummary(true)
      .setSound(alarmSound)
      .setChannelId(ALARM_RECEIVER_NOTIFICATION_CHANNEL_ID)
      .setContentIntent(pendingIntent)
      .build()

    notificationManagerCompat.notify(ALARM_RECEIVER_NOTIFICATION_ID, alarmNotification)
  }
}