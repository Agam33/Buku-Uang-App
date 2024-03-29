package com.ra.bkuang.data.local.database.converter

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

class DbDateTypeConverter {

  @TypeConverter
  fun localDateTimeToDate(value: LocalDateTime?): Date? {
    return value?.let {
      Date.from(it.atZone(ZoneId.systemDefault()).toInstant())
    }
  }

  @TypeConverter
  fun toTimeStamp(date: Date?): Long? {
    return date?.let {
      it.time
    }
  }

  @TypeConverter
  fun toDate(timeStamp: Long?): Date? {
    return timeStamp?.let {
      Date(it)
    }
  }
}