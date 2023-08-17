package com.ra.budgetplan.presentation.ui.transaction.adapter

abstract class AbsListItem<T> {
  abstract fun getSize(): Int
  abstract fun getItem(): T
  abstract fun getType(): Int
}