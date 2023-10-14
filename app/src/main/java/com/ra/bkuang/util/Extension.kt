package com.ra.bkuang.util

import android.animation.ValueAnimator
import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.view.WindowManager
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.ra.bkuang.presentation.ui.MainActivity
import com.ra.bkuang.service.TransactionDailyWorker
import com.ra.bkuang.util.Constants.REQUIRED_STORAGE_PERMISSION
import java.text.NumberFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

object Extension {

  fun Context.setExactAndAllowWhileIdleAlarm(
    calendar: Calendar,
    pendingIntent: PendingIntent,
  ) {
    val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
  }

  fun Context.cancelAlarm(
    pendingIntent: PendingIntent
  ) {
    val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
    alarmManager.cancel(pendingIntent)
  }

  fun Context.setDailyWorker(calendar: Calendar) {
    val future: Long = calendar.timeInMillis
    val now: Long  = System.currentTimeMillis()
    val initialDelay: Long = future - now

    val constraints = Constraints.Builder()
      .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
      .build()

    val periodicRequest = PeriodicWorkRequest.Builder(
      TransactionDailyWorker::class.java,
      1,
      TimeUnit.DAYS
    )
      .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
      .setConstraints(constraints)
      .build()

    WorkManager.getInstance(this)
      .enqueueUniquePeriodicWork(
        Constants.TRANSACTION_DAILY_WORKER_NAME,
        ExistingPeriodicWorkPolicy.UPDATE,
        periodicRequest
      )
  }

  fun Context.cancelDailyWorker(uniqueName: String) {
    WorkManager.getInstance(this)
      .cancelUniqueWork(uniqueName)
  }

  fun Fragment.requestStoragePermission(): Boolean {
    return REQUIRED_STORAGE_PERMISSION.all {
      ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }
  }

  fun Activity.requestStoragePermission(): Boolean {
    return REQUIRED_STORAGE_PERMISSION.all {
      ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }
  }

  fun Context.restartActivity() {
    val i = Intent(this, MainActivity::class.java).apply {
      flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
    }

    startActivity(i)

    if(this is Activity) {
      finish()
    }
  }

  fun Int.toPercent(maxValue: Int): Double {
    return (this * 100.0) / maxValue
  }

  fun Double.toPercentText(): String {
    val percentBuilder = java.lang.StringBuilder()
    percentBuilder.append(String.format("%.1f", this))
    percentBuilder.append("%")
    return percentBuilder.toString()
  }

  fun LocalDateTime.toCalendar(): Calendar {
    val zonedDateTime = this.atZone(ZoneId.systemDefault())
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = zonedDateTime.toInstant().toEpochMilli()
    return calendar
  }

  fun LocalDate.toStringFormat(format: String, locale: Locale = Locale.ENGLISH): String {
    val formatter = DateTimeFormatter.ofPattern(format, locale)
    return format(formatter)
  }

  fun LocalDateTime.toStringFormat(format: String, locale: Locale = Locale.ENGLISH): String {
    val formatter = DateTimeFormatter.ofPattern(format, locale)
    return format(formatter)
  }

  fun Int.checkTimeFormat(): String {
    return if(this <= 9) "0$this" else "$this"
  }

  fun Context.getStringResource(resId: Int, vararg formatArgs: Any?): String {
    return resources.getString(resId, *formatArgs)
  }

  fun Activity.showShortToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
  }

  fun Activity.showLongToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
  }

  fun Fragment.showShortToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
  }

  fun Fragment.showLongToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
  }


  fun View.collapsedHeight(
    duration: Long,
    targetHeight: Int = 0,
    action: () -> Unit = {}
  ) {
    val prevHeight = height
    val valueAnimator: ValueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight)
    valueAnimator.addUpdateListener { animation ->
      layoutParams.height = animation.animatedValue as Int
      requestLayout()
    }

    valueAnimator.interpolator = DecelerateInterpolator()
    valueAnimator.duration = duration
    valueAnimator.start()

    action()
  }

  fun View.expandedHeight(
    duration: Long,
    targetHeight: Int = WindowManager.LayoutParams.WRAP_CONTENT,
    action: () -> Unit = {}
  ) {
    val prevHeight = height

    visibility = View.VISIBLE

    val valueAnimator: ValueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight)
    valueAnimator.addUpdateListener { animation ->
      layoutParams.height = animation.animatedValue as Int
      requestLayout()
    }

    valueAnimator.interpolator = DecelerateInterpolator()
    valueAnimator.duration = duration
    valueAnimator.start()

    action()
  }

  fun AppCompatActivity.setupNoActionbar(
    toolbar: androidx.appcompat.widget.Toolbar?,
    title: String = "",
  ) {
    setSupportActionBar(toolbar)
    if(title.isNotEmpty()) supportActionBar?.title = title
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
  }

  fun SimpleDateFormat.millisToString(timeMillis: Long): String {
    val date = Date(timeMillis)
    return this.format(date)
  }

  fun String.firstCharUppercase(): String {
    if (isEmpty())
      throw NoSuchElementException("Char sequence is empty.")
    val ch = this[0]
    val result = StringBuilder()
    result.append(ch)
    for(i in 1 until this.length) {
      result.append(this[i].lowercase())
    }
    return result.toString()
  }

  fun Int.toFormatRupiah(locale: Locale = Locale("id", "ID")): String {
    val formatRupiah = NumberFormat.getCurrencyInstance(locale)
    return formatRupiah.format(this).replace("Rp", "Rp ")
  }

  fun Long.toFormatRupiah(locale: Locale = Locale("id", "ID")): String {
    val formatRupiah = NumberFormat.getCurrencyInstance(locale)
    return formatRupiah.format(this).replace("Rp", "Rp ")
  }

  inline fun <reified T: Parcelable> Intent.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
  }

  inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
  }
}