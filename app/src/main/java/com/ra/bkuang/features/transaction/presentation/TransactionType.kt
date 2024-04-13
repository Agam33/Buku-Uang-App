package com.ra.bkuang.features.transaction.presentation

enum class TransactionType {
  INCOME, EXPENSE, TRANSFER;

  companion object {
    fun getTransactionType(type: String): TransactionType =
      when(type.uppercase()) {
        EXPENSE.name -> EXPENSE
        INCOME.name -> INCOME
        else -> TRANSFER
      }

    fun getTransactionTypeID(type: String): TransactionType =
      when(type.uppercase()) {
        "PENGELUARAN" -> EXPENSE
        "PENDAPATAN" -> INCOME
        else  -> TRANSFER
      }
  }
}






