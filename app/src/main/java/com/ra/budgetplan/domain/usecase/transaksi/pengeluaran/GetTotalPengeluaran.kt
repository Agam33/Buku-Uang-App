package com.ra.budgetplan.domain.usecase.transaksi.pengeluaran

import kotlinx.coroutines.flow.Flow

interface GetTotalPengeluaran {
  fun invoke(): Flow<Long>
}