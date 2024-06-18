package com.ra.bkuang.features.debt.alarm

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
import com.ra.bkuang.common.receiver.AlarmReceiver
import com.ra.bkuang.common.util.Constants
import com.ra.bkuang.common.di.AppAlarmManagerQualifier
import com.ra.bkuang.common.di.AppNotificationManagerQualifier
import com.ra.bkuang.common.di.IoCoroutineScopeQualifier
import com.ra.bkuang.features.debt.domain.model.HutangModel
import com.ra.bkuang.features.debt.presentation.DebtFragment
import com.ra.bkuang.features.debt.presentation.detail.DetailDebtActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject.Inject

class DebtAlarmManagerManagerImpl @Inject constructor(
  @ApplicationContext private val ctx: Context,
  @IoCoroutineScopeQualifier private val ioScope: CoroutineScope,
  @AppAlarmManagerQualifier private val alarmManager: AlarmManager,
  @AppNotificationManagerQualifier private val notificationManager: NotificationManager,
): DebtAlarmManager {

  override fun setAlarm(model: HutangModel, calendar: Calendar, callback: (HutangModel) -> Unit) {
    val alarmId = model.uuid.hashCode()
    val sdf = SimpleDateFormat(Constants.DATE_PATTERN, Constants.LOCALE_ID)
    model.idPengingat = alarmId
    model.pengingatAktif = true
    model.tglPengingat = sdf.format(calendar.time)

    alarmManager.setExactAndAllowWhileIdle(
      AlarmManager.RTC_WAKEUP,
      calendar.timeInMillis,
      createPendingIntent(alarmId, model)
    )
    callback(model)
  }

  override fun cancelAlarm(model: HutangModel, callback: (HutangModel) -> Unit) {
    alarmManager.cancel(createPendingIntent(model.idPengingat, model))

    ioScope.launch {
      model.idPengingat = Int.MAX_VALUE
      model.tglPengingat = ""
      model.pengingatAktif = false

      callback.invoke(model)
    }
  }

  private fun createPendingIntent(alarmId: Int, model: HutangModel): PendingIntent {
    val intent = Intent(ctx, AlarmReceiver::class.java).apply {
      putExtra(AlarmReceiver.ALARM_CATEGORY, AlarmCategory.DEBT.name)
      putExtra(DEBT_MODEL_ID, model.uuid.toString())
      putExtra(DEBT_ALARM_EXTRA_TITLE, model.nama)
      putExtra(DEBT_ALARM_EXTRA_ID, alarmId)
    }
    return PendingIntent.getBroadcast(ctx, alarmId, intent, PendingIntent.FLAG_MUTABLE)
  }

  override fun showNotification(
    context: Context,
    debtModelId: String,
    title: String,
  ) {
    val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    
    val channel = NotificationChannel(
      ALARM_RECEIVER_NOTIFICATION_CHANNEL_ID,
      ALARM_RECEIVER_NOTIFICATION_CHANNEL_NAME,
      NotificationManager.IMPORTANCE_DEFAULT
    )

    notificationManager.createNotificationChannel(channel)

    val intent = Intent(ctx, DetailDebtActivity::class.java).apply {
      putExtra(DebtFragment.DEBT_MODEL_ID, debtModelId)
    }

    val pendingIntent = PendingIntent.getActivity(
      ctx, 0,
      intent,
      PendingIntent.FLAG_MUTABLE
    )

    val alarmNotification = NotificationCompat.Builder(ctx,
      ALARM_RECEIVER_NOTIFICATION_CHANNEL_ID
    )
      .setContentTitle(String.format(ctx.getString(R.string.msg_title_debt_notification), title))
      .setSmallIcon(R.drawable.book_icon_v2)
      .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
      .setSound(alarmSound)
      .setChannelId(ALARM_RECEIVER_NOTIFICATION_CHANNEL_ID)
      .setContentIntent(pendingIntent)
      .build()

    notificationManager.notify(ALARM_RECEIVER_NOTIFICATION_ID, alarmNotification)
  }

  companion object {
    private const val DEBT_MODEL_ID = "debt-model-id"
    const val DEBT_ALARM_EXTRA_TITLE = "debt-alarm-extra-title"
    const val DEBT_ALARM_EXTRA_ID = "debt-alarm-extra-id"
    private const val ALARM_RECEIVER_NOTIFICATION_ID = 202
    private const val ALARM_RECEIVER_NOTIFICATION_CHANNEL_NAME = "alarm-receiver"
    private const val ALARM_RECEIVER_NOTIFICATION_CHANNEL_ID = "alarm-receiver-channel-1"
  }
}