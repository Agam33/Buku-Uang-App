package com.ra.budgetplan.util

import android.app.Activity
import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.NumberFormat
import java.util.Locale

fun Activity.shortToast(message: String) {
  Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Activity.longToast(message: String) {
  Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun AppCompatActivity.setupNoActionbar(toolbar: androidx.appcompat.widget.Toolbar?) {
  setSupportActionBar(toolbar)
  supportActionBar?.setDisplayHomeAsUpEnabled(true)
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
  val formatRupiah = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
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
