package com.ra.bkuang.alarm

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import com.ra.bkuang.R
import com.ra.bkuang.di.AppAlarmManagerQualifier
import com.ra.bkuang.di.AppNotificationManagerQualifier
import com.ra.bkuang.di.IoCoroutineScopeQualifier
import com.ra.bkuang.domain.model.HutangModel
import com.ra.bkuang.domain.usecase.hutang.UpdateHutang
import com.ra.bkuang.presentation.ui.debt.DebtFragment
import com.ra.bkuang.presentation.ui.debt.DetailDebtActivity
import com.ra.bkuang.receiver.AlarmReceiver
import com.ra.bkuang.util.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject.Inject

class DebtAlarmManagerImpl @Inject constructor(
  @ApplicationContext private val ctx: Context,
  @IoCoroutineScopeQualifier private val ioScope: CoroutineScope,
  @AppAlarmManagerQualifier private val alarmManager: AlarmManager,
  @AppNotificationManagerQualifier private val notificationManager: NotificationManager,
  private val updateHutang: UpdateHutang
): DebtAlarm {

  override fun setAlarm(model: HutangModel, calendar: Calendar) {
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

    ioScope.launch {
      updateHutang.invoke(model)
    }
  }

  override fun cancelAlarm(model: HutangModel) {
    alarmManager.cancel(createPendingIntent(model.idPengingat, model))

    ioScope.launch {
      model.idPengingat = Int.MAX_VALUE
      model.tglPengingat = ""
      model.pengingatAktif = false

      updateHutang.invoke(model)
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
    title: String
  ) {
    val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    
    val channel = NotificationChannel(
      Constants.ALARM_RECEIVER_NOTIFICATION_CHANNEL_ID,
      Constants.ALARM_RECEIVER_NOTIFICATION_CHANNEL_NAME,
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
      Constants.ALARM_RECEIVER_NOTIFICATION_CHANNEL_ID
    )
      .setContentTitle(String.format(ctx.getString(R.string.msg_title_debt_notification), title))
      .setSmallIcon(R.drawable.book_icon_v2)
      .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
      .setSound(alarmSound)
      .setChannelId(Constants.ALARM_RECEIVER_NOTIFICATION_CHANNEL_ID)
      .setContentIntent(pendingIntent)
      .build()

    notificationManager.notify(Constants.ALARM_RECEIVER_NOTIFICATION_ID, alarmNotification)
  }

  companion object {
    const val DEBT_MODEL_ID = "debt-model-id"
    const val DEBT_ALARM_EXTRA_TITLE = "debt-alarm-extra-title"
    const val DEBT_ALARM_EXTRA_ID = "debt-alarm-extra-id"
  }
}