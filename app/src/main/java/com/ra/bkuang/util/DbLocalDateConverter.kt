package com.ra.bkuang.util

import androidx.room.TypeConverter
import java.time.LocalDate

class DbLocalDateConverter {
  @TypeConverter
  fun fromTimestamp(value: Long?): LocalDate? {
    return value?.let { LocalDate.ofEpochDay(it) }
  }

  @TypeConverter
  fun toTimestamp(date: LocalDate?): Long? {
    return date?.toEpochDay()
  }
}