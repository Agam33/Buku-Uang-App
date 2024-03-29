package com.ra.bkuang.presentation.ui.transaction.adapter

import com.ra.bkuang.domain.model.TransactionDetail

interface OnDayItemClickListener {
  fun onClickDayItem(dayItem: TransactionDetail)
}