package com.ra.budgetplan.util

import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import java.text.NumberFormat
import java.util.Locale

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
