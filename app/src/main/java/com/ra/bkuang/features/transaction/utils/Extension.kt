package com.ra.bkuang.features.transaction.utils

import com.ra.bkuang.common.util.DateViewType
import com.ra.bkuang.common.util.getDateViewType
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters
import java.util.Calendar

fun LocalDate.dateByViewType(viewType: String): Pair<LocalDateTime, LocalDateTime> {
  return when(getDateViewType(viewType)) {
    DateViewType.MONTHLY -> {
      val calendar = Calendar.getInstance()
      calendar.set(Calendar.YEAR, this.year)
      calendar.set(Calendar.MONTH,this.month.value - 1)
      val localDateTime = LocalDateTime.ofInstant(calendar.toInstant(), ZoneId.systemDefault())

      val fromDate = localDateTime.withDayOfMonth(1)
        .withHour(0)
        .withMinute(0)
      val toDate = localDateTime.with(TemporalAdjusters.lastDayOfMonth())
        .withHour(23)
        .withMinute(59)

      fromDate to toDate
    }
    DateViewType.DAILY -> {
      val fromDate = this.atStartOfDay()
      val toDate = this.atTime(LocalTime.MAX)

      fromDate to toDate
    }
  }
}