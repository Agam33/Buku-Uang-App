package com.ra.bkuang.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.ra.bkuang.R
import com.ra.bkuang.presentation.ui.transaction.main.MainActivity
import com.ra.bkuang.util.Constants

class TransactionDailyWorker(
  private val ctx: Context,
  workerParams: WorkerParameters
): Worker(ctx, workerParams) {

  override fun doWork(): Result {
    showAlarmTransactionNotification()
    return Result.success()
  }

  private fun showAlarmTransactionNotification() {
    val intent = Intent(ctx, MainActivity::class.java).apply {
      flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

    val notificationManagerCompat = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    val channel = NotificationChannel(
        Constants.ALARM_TRANSACTION_NOTIFICATION_CHANNEL_ID,
        Constants.ALARM_TRANSACTION_NOTIFICATION_CHANNEL_NAME,
      NotificationManager.IMPORTANCE_DEFAULT
    )

    val pendingIntent = PendingIntent.getActivity(
      ctx, 0,
      intent,
      PendingIntent.FLAG_MUTABLE
    )

    val alarmNotification = NotificationCompat.Builder(ctx,
        Constants.ALARM_TRANSACTION_NOTIFICATION_CHANNEL_ID
    )
      .setContentTitle(ctx.resources.getString(R.string.txt_alarm_transaction))
      .setSmallIcon(R.drawable.empty_note)
      .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
      .setContentText(ctx.getString(R.string.txt_transaction_alarm_msg))
      .setSound(alarmSound)
      .setChannelId(Constants.ALARM_TRANSACTION_NOTIFICATION_CHANNEL_ID)
      .setContentIntent(pendingIntent)
      .build()

    notificationManagerCompat.createNotificationChannel(channel)
    notificationManagerCompat.notify(Constants.ALARM_RECEIVER_NOTIFICATION_ID, alarmNotification)
  }
}