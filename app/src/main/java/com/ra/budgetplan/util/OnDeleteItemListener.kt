package com.ra.budgetplan.util

interface OnDeleteItemListener<T> {
  fun onDeleteItem(item: T)
}