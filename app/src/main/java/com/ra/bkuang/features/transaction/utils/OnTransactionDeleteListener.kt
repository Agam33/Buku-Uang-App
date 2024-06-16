package com.ra.bkuang.features.transaction.utils

interface OnTransactionDeleteListener<T> {
  fun onDeleteItem(item: T)
}