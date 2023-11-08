package com.ra.bkuang.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import com.ra.bkuang.R
import com.ra.bkuang.data.local.HutangLocalDataSource
import com.ra.bkuang.domain.mapper.toEntity
import com.ra.bkuang.domain.mapper.toModel
import com.ra.bkuang.presentation.ui.MainActivity
import com.ra.bkuang.presentation.ui.debt.DebtFragment
import com.ra.bkuang.presentation.ui.debt.DebtFragment.Companion.DEBT_MODEL_ID
import com.ra.bkuang.presentation.ui.debt.DetailDebtActivity
import com.ra.bkuang.util.Constants.ALARM_RECEIVER_NOTIFICATION_CHANNEL_ID
import com.ra.bkuang.util.Constants.ALARM_RECEIVER_NOTIFICATION_CHANNEL_NAME
import com.ra.bkuang.util.Constants.ALARM_RECEIVER_NOTIFICATION_ID
import com.ra.bkuang.util.Constants.ALARM_TRANSACTION_NOTIFICATION_CHANNEL_ID
import com.ra.bkuang.util.Constants.ALARM_TRANSACTION_NOTIFICATION_CHANNEL_NAME
import com.ra.bkuang.util.Constants.coroutineIOThread
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver: BroadcastReceiver() {

  @Inject
  lateinit var localDataSource: HutangLocalDataSource

  override fun onReceive(context: Context, intent: Intent) {
    val alarmCategory = intent.getStringExtra(ALARM_CATEGORY) ?: ""
    context.startForegroundService(intent)
    when(AlarmCategory.getAlarmCategoryByString(alarmCategory)) {
      AlarmCategory.DEBT -> {
        Timber.tag("AlarmReceiver.class").d("AlarmCategory.DEBT")
        onAlarmHutang(context, intent)
      }
      AlarmCategory.TRANSACTION -> {
        Timber.tag("AlarmReceiver.class").d("AlarmCategory.TRANSACTION")
        onAlarmTransaction(context)
      }
    }
  }

  private fun onAlarmTransaction(ctx: Context) {
    val intent = Intent(ctx, MainActivity::class.java).apply {
      flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

    val notificationManagerCompat = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    val channel = NotificationChannel(
      ALARM_TRANSACTION_NOTIFICATION_CHANNEL_ID,
      ALARM_TRANSACTION_NOTIFICATION_CHANNEL_NAME,
      NotificationManager.IMPORTANCE_DEFAULT
    )

    val pendingIntent = PendingIntent.getActivity(
      ctx, 0,
      intent,
      PendingIntent.FLAG_MUTABLE
    )

    val alarmNotification = NotificationCompat.Builder(ctx, ALARM_TRANSACTION_NOTIFICATION_CHANNEL_ID)
      .setContentTitle(ctx.resources.getString(R.string.txt_alarm_transaction))
      .setSmallIcon(R.drawable.empty_note)
      .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
      .setContentText(ctx.getString(R.string.txt_transaction_alarm_msg))
      .setSound(alarmSound)
      .setChannelId(ALARM_TRANSACTION_NOTIFICATION_CHANNEL_ID)
      .setContentIntent(pendingIntent)
      .build()

    notificationManagerCompat.createNotificationChannel(channel)
    notificationManagerCompat.notify(ALARM_RECEIVER_NOTIFICATION_ID, alarmNotification)
  }

  private fun onAlarmHutang(ctx: Context, intent: Intent) {
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

    showHutangNotification(
      ctx,
      debtModelId ?: "",
      alarmTitle ?: ""
    )
  }

  private fun showHutangNotification(
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
      putExtra(DEBT_MODEL_ID, debtModelId)
    }

    val pendingIntent = PendingIntent.getActivity(
      context, 0,
      intent,
      PendingIntent.FLAG_MUTABLE
    )

    val alarmNotification = NotificationCompat.Builder(context, ALARM_RECEIVER_NOTIFICATION_CHANNEL_ID)
      .setContentTitle(String.format(context.getString(R.string.msg_title_debt_notification), title))
      .setSmallIcon(R.drawable.book_icon_v2)
      .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
      .setSound(alarmSound)
      .setChannelId(ALARM_RECEIVER_NOTIFICATION_CHANNEL_ID)
      .setContentIntent(pendingIntent)
      .build()

    notificationManagerCompat.notify(ALARM_RECEIVER_NOTIFICATION_ID, alarmNotification)
  }

  companion object {
    const val ALARM_CATEGORY = "alarm-category"
  }
}