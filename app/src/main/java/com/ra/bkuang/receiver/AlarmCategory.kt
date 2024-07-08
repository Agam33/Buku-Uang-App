package com.ra.bkuang.receiver

enum class AlarmCategory {
  DEBT, TRANSACTION;

  companion object {
    fun getAlarmCategoryByString(type: String): AlarmCategory {
      return when(type) {
        DEBT.name -> DEBT
        TRANSACTION.name -> TRANSACTION
        else -> throw IllegalArgumentException("Alarm category not found!")
      }
    }
  }
}