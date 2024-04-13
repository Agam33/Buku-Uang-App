package com.ra.bkuang.features.transaction.alarm

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import com.ra.bkuang.R
import com.ra.bkuang.common.receiver.AlarmCategory
import com.ra.bkuang.di.AppAlarmManagerQualifier
import com.ra.bkuang.di.AppNotificationManagerQualifier
import com.ra.bkuang.main.MainActivity
import com.ra.bkuang.common.receiver.AlarmReceiver
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Calendar
import java.util.TimeZone
import javax.inject.Inject

class TransactionAlarmManagerManagerImpl @Inject constructor(
  @ApplicationContext private val ctx: Context,
  @AppAlarmManagerQualifier private val alarmManager: AlarmManager,
  @AppNotificationManagerQualifier private val notificationManager: NotificationManager
): TransactionAlarmManager {

  override fun setAlarm(calendar: Calendar) {
    alarmManager.setExactAndAllowWhileIdle(
      AlarmManager.RTC_WAKEUP,
      calendar.timeInMillis,
      createPendingIntent(
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE)
      )
    )
  }

  override fun setOnNextDay(hour: Int, minute: Int) {
    val calendar = Calendar.getInstance(TimeZone.getDefault())
    calendar.add(Calendar.DAY_OF_MONTH, 1)
    calendar.set(Calendar.MINUTE, minute)
    calendar.set(Calendar.HOUR_OF_DAY, hour)

    setAlarm(calendar)
  }

  override fun cancelAlarm() {
    alarmManager.cancel(createPendingIntent(0, 0))
  }

  override fun showNotification(ctx: Context) {
    val intent = Intent(ctx, MainActivity::class.java).apply {
      flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

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

    val alarmNotification = NotificationCompat.Builder(ctx,
      ALARM_TRANSACTION_NOTIFICATION_CHANNEL_ID
    )
      .setContentTitle(ctx.resources.getString(R.string.txt_alarm_transaction))
      .setSmallIcon(R.drawable.empty_note)
      .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
      .setContentText(ctx.getString(R.string.txt_transaction_alarm_msg))
      .setSound(alarmSound)
      .setChannelId(ALARM_TRANSACTION_NOTIFICATION_CHANNEL_ID)
      .setContentIntent(pendingIntent)
      .build()

    notificationManager.createNotificationChannel(channel)
    notificationManager.notify(ALARM_RECEIVER_NOTIFICATION_ID, alarmNotification)
  }

  private fun createPendingIntent(hour: Int, minute: Int): PendingIntent {
    val i = Intent(ctx, AlarmReceiver::class.java).apply {
      putExtra(AlarmReceiver.ALARM_CATEGORY, AlarmCategory.TRANSACTION.name)
      putExtra(TRANSACTION_HOUR, hour)
      putExtra(TRANSACTION_MINUTE, minute)
    }
    return PendingIntent.getBroadcast(ctx, TRANSACTION_ALARM_ID, i, PendingIntent.FLAG_MUTABLE)
  }

  companion object {
    const val TRANSACTION_HOUR = "transaction-hour"
    const val TRANSACTION_MINUTE = "transaction-minute"
    private const val TRANSACTION_ALARM_ID = 99
    private const val ALARM_TRANSACTION_NOTIFICATION_CHANNEL_ID = "alarm-transaction-ch-1"
    private const val ALARM_RECEIVER_NOTIFICATION_ID = 201
    private const val ALARM_TRANSACTION_NOTIFICATION_CHANNEL_NAME = "alarm-transaction"
  }
}