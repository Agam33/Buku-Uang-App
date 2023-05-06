package com.ra.budgetplan.util

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class DbLocalDateTimeConverter {
  @TypeConverter
  fun dateToString(date: LocalDateTime?): String? {
    return date?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
  }

  @TypeConverter
  fun fromString(value: String?): LocalDateTime? {
    return LocalDateTime.parse(
      value,
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    )
  }

  @TypeConverter
  fun dateToTimestamp(date: LocalDateTime?): Long? {
    return date?.atZone(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()
  }

  @TypeConverter
  fun fromTimestamp(value: Long?): LocalDateTime? {
    return value?.let {
      Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDateTime()
    }
  }
}