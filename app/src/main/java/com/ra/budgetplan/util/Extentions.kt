package com.ra.budgetplan.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.ra.budgetplan.presentation.ui.MainActivity
import java.io.File
import java.text.NumberFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun Fragment.requestStoragePermission(): Boolean {
  return REQUIRED_STORAGE_PERMISSION.all {
    ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
  }
}

fun Activity.requestStoragePermission(): Boolean {
  return REQUIRED_STORAGE_PERMISSION.all {
    ContextCompat.checkSelfPermission(this.baseContext, it) == PackageManager.PERMISSION_GRANTED
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

fun Int.toFormatRupiah(): String {
  val formatRupiah = NumberFormat.getCurrencyInstance(LOCALE_ID)
  return formatRupiah.format(this).replace("Rp", "Rp ")
}

fun Long.toFormatRupiah(): String {
  val formatRupiah = NumberFormat.getCurrencyInstance(LOCALE_ID)
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
