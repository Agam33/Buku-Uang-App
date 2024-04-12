package com.ra.bkuang.domain.usecase.transaksi

import java.util.Calendar

interface SetTransactionAlarm {
  suspend fun invoke(calendar: Calendar): Boolean
}