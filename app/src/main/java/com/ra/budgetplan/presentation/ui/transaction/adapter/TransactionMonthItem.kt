package com.ra.budgetplan.presentation.ui.transaction.adapter

import com.ra.budgetplan.util.RvGroup

class TransactionMonthItem<T>(
  private var item: RvGroup<String, T>
): AbsListItem<RvGroup<String, T>>() {

  override fun getSize(): Int = item.size

  override fun getItem(): RvGroup<String, T> = item

  override fun getType(): Int = DateViewType.MONTHLY.ordinal
}