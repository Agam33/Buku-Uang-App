package com.ra.bkuang.presentation.ui.features.transaction

enum class TransactionType {
  INCOME, EXPENSE, TRANSFER;
}

fun getTransactionType(type: String): TransactionType =
  when(type.uppercase()) {
    TransactionType.EXPENSE.name -> TransactionType.EXPENSE
    TransactionType.INCOME.name -> TransactionType.INCOME
    else -> TransactionType.TRANSFER
  }

fun getTransactionTypeID(type: String): TransactionType =
  when(type.uppercase()) {
    "PENGELUARAN" -> TransactionType.EXPENSE
    "PENDAPATAN" -> TransactionType.INCOME
    else  -> TransactionType.TRANSFER
  }


