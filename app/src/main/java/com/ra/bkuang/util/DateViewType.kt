package com.ra.bkuang.util

enum class DateViewType {
  DAILY, MONTHLY
}

fun getDateViewType(type: String): DateViewType {
  return when(type) {
    DateViewType.MONTHLY.name -> DateViewType.MONTHLY
    else -> DateViewType.DAILY
  }
}