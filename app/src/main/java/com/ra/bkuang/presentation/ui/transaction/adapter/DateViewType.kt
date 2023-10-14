package com.ra.bkuang.presentation.ui.transaction.adapter

enum class DateViewType {
  DAILY, MONTHLY
}

fun getDateViewType(type: String): DateViewType {
  return when(type) {
    DateViewType.MONTHLY.name -> DateViewType.MONTHLY
    else -> DateViewType.DAILY
  }
}