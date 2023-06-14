package com.ra.budgetplan.presentation.ui.transaction.adapter

class TransactionDayItem<T>(
  private val item: List<T>
): AbsListItem<List<T>>() {



  override fun getSize(): Int = item.size

  override fun getItem(): List<T> = item

  override fun getType(): Int = DateViewType.DAILY.ordinal

}