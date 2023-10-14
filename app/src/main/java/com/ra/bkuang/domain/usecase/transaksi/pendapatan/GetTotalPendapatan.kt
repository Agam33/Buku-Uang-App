package com.ra.bkuang.domain.usecase.transaksi.pendapatan

import kotlinx.coroutines.flow.Flow

interface GetTotalPendapatan {
  fun invoke(): Flow<Long>
}