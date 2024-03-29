package com.ra.bkuang.util

import android.Manifest
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.DecelerateInterpolator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

object Constants {

  const val DB_NAME = "bk_uang.db"
  const val DB_BACKUP_FILE_NAME = "bk_uang"
  const val DB_NAME_SHM = "bk_uang.db-shm"
  const val DB_NAME_WAL = "bk_uang.db-wal"
  const val DAILY_DATE_FORMAT = "MMMM d, yyyy"
  const val MONTHLY_DATE_FORMAT = "MMMM, yyyy"
  const val FILE_USER_SETTING_PREF = "user_setting_pref.pb"
  const val FILE_USER_SETTING_SHARED_PREF = "user_setting_shared_pref"
  const val DATE_PATTERN = "yyyy-MM-dd"
  const val DATE_TIME_FORMATTER = "yyyy-MM-dd HH:mm"
  const val ALARM_RECEIVER_NOTIFICATION_ID = 201
  const val ALARM_RECEIVER_NOTIFICATION_CHANNEL_ID = "alarm-receiver-channel-1"
  const val ALARM_RECEIVER_NOTIFICATION_CHANNEL_NAME = "alarm-receiver"
  const val REQUEST_READ_AND_WRITE_EXTERNAL_STORAGE = 102
  const val TAG_TIME_PICKER_FRAGMENT = "tag-time-picker-fragment"
  const val ALARM_TRANSACTION_NOTIFICATION_CHANNEL_ID = "alarm-transaction-ch-1"
  const val ALARM_TRANSACTION_NOTIFICATION_CHANNEL_NAME = "alarm-transaction"
  const val TRANSACTION_ALARM_ID = 3000001
  const val TRANSACTION_DAILY_WORKER_NAME = "transaction-daily-worker-name"

  val LOCALE_ID = Locale("id", "ID")

  val REQUIRED_STORAGE_PERMISSION = mutableListOf(
    Manifest.permission.WRITE_EXTERNAL_STORAGE,
    Manifest.permission.READ_EXTERNAL_STORAGE
  )

}