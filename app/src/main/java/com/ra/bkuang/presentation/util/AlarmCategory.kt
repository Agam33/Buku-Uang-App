package com.ra.bkuang.presentation.util

enum class AlarmCategory {
  DEBT, TRANSACTION;

  companion object {
    fun getAlarmCategoryByString(type: String): AlarmCategory {
      return when(type) {
        DEBT.name -> DEBT
        TRANSACTION.name -> TRANSACTION
        else -> throw IllegalArgumentException("Alarm Category doesn't exist!!")
      }
    }
  }
}