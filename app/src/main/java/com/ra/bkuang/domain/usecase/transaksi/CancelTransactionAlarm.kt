package com.ra.bkuang.domain.usecase.transaksi

interface CancelTransactionAlarm {
  suspend fun invoke(): Boolean
}