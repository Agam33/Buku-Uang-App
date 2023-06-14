package com.ra.budgetplan.presentation.ui.transaction

enum class TransactionType {
  INCOME, EXPENSE, TRANSFER;
}

fun getTransactionType(type: String): TransactionType =
  when(type.uppercase()) {
    TransactionType.EXPENSE.name -> TransactionType.EXPENSE
    TransactionType.INCOME.name -> TransactionType.INCOME
    else -> TransactionType.TRANSFER
  }

