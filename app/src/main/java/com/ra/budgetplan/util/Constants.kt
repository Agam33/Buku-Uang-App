package com.ra.budgetplan.util

import android.animation.ValueAnimator
import android.view.View
import android.view.animation.DecelerateInterpolator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.time.LocalDate
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

fun expandToRight(v: View, duration: Long, targetWidth: Int) {
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

fun collapsedToLeft(v: View, duration: Long, targetWidth: Int) {
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
