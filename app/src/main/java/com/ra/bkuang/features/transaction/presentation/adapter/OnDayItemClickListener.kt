package com.ra.bkuang.features.transaction.presentation.adapter

import com.ra.bkuang.features.transaction.domain.model.TransactionDetail

interface OnDayItemClickListener {
  fun onClickDayItem(dayItem: TransactionDetail)
}