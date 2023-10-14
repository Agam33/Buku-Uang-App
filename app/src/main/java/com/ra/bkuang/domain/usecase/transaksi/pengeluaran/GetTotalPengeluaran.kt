package com.ra.bkuang.domain.usecase.transaksi.pengeluaran

import kotlinx.coroutines.flow.Flow

interface GetTotalPengeluaran {
  fun invoke(): Flow<Long>
}