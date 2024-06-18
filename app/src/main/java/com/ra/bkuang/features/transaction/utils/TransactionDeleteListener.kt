package com.ra.bkuang.features.transaction.utils

interface TransactionDeleteListener<T> {
  fun onDeleteItem(item: T)
}