package com.ra.budgetplan.util

import android.animation.ValueAnimator
import android.view.View
import android.view.animation.DecelerateInterpolator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters
import java.util.Calendar
import java.util.Locale

const val DAILY_DATE_FORMAT = "MMMM d, yyyy"
const val MONTHLY_DATE_FORMAT = "MMMM, yyyy"
const val FILE_USER_SETTING_PREF = "user_setting_pref.pb"
const val DATE_PATTERN = "yyyy-MM-dd"
const val DATE_TIME_FORMATTER = "yyyy-MM-dd HH:mm"

val LOCALE_ID = Locale("id", "ID")

fun coroutineIOThread(action: suspend () -> Unit) {
  CoroutineScope(Dispatchers.IO).launch {
    action()
  }
}

fun LocalDate.toMonthlyTime(): Pair<LocalDateTime, LocalDateTime> {
  val calendar = Calendar.getInstance()
  calendar.set(Calendar.YEAR, this.year)
  calendar.set(Calendar.MONTH, this.month.value - 1)
  val localDateTime = LocalDateTime.ofInstant(calendar.toInstant(), ZoneId.systemDefault())

  val fromDate = localDateTime.withDayOfMonth(1)
    .withHour(0)
    .withMinute(0)
  val toDate = localDateTime.with(TemporalAdjusters.lastDayOfMonth())
    .withHour(23)
    .withMinute(59)

  return fromDate to toDate
}

fun calculatePercent(currValue: Int, maxValue: Long): Double =
  ((currValue * 1.0) / (maxValue * 1.0 )) * 100.0

fun expanded(v: View, duration: Long, targetWidth: Int) {
  val prevWidth = v.width

  v.visibility = View.VISIBLE
  val valueAnimator: ValueAnimator = ValueAnimator.ofInt(prevWidth, targetWidth)
  valueAnimator.addUpdateListener { animation ->
    v.layoutParams.width = animation.animatedValue as Int
    v.requestLayout()
  }

  valueAnimator.interpolator = DecelerateInterpolator()
  valueAnimator.duration = duration
  valueAnimator.start()
}

fun collapsed(v: View, duration: Long, targetWidth: Int) {
  val prevWidth = v.width
  val valueAnimator: ValueAnimator = ValueAnimator.ofInt(prevWidth, targetWidth)
  valueAnimator.addUpdateListener { animation ->
    v.layoutParams.width = animation.animatedValue as Int
    v.requestLayout()
  }

  valueAnimator.interpolator = DecelerateInterpolator()
  valueAnimator.duration = duration
  valueAnimator.start()
}
