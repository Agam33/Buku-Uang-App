package com.ra.bkuang.data.local.database.converter

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