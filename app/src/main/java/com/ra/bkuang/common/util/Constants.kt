package com.ra.bkuang.common.util

import android.Manifest
import java.util.Locale

object Constants {

  const val DB_NAME = "bk_uang.db"
  const val DB_NAME_SHM = "bk_uang.db-shm"
  const val DB_NAME_WAL = "bk_uang.db-wal"
  const val DAILY_DATE_FORMAT = "MMMM d, yyyy"
  const val MONTHLY_DATE_FORMAT = "MMMM, yyyy"
  const val DATE_PATTERN = "yyyy-MM-dd"
  const val DATE_TIME_FORMATTER = "yyyy-MM-dd HH:mm"
  const val TAG_TIME_PICKER_FRAGMENT = "tag-time-picker-fragment"

  val LOCALE_ID = Locale("id", "ID")

  val REQUIRED_STORAGE_PERMISSION = mutableListOf(
    Manifest.permission.WRITE_EXTERNAL_STORAGE,
    Manifest.permission.READ_EXTERNAL_STORAGE
  )

}