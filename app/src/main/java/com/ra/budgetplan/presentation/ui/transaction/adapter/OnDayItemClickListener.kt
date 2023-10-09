package com.ra.budgetplan.presentation.ui.transaction.adapter

import com.ra.budgetplan.presentation.ui.transaction.TransactionDetail

interface OnDayItemClickListener {
  fun onClickDayItem(dayItem: TransactionDetail)
}