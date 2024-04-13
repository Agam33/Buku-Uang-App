package com.ra.bkuang.features.transaction.domain.usecase.pengeluaran

import kotlinx.coroutines.flow.Flow

interface GetTotalPengeluaran {
  fun invoke(): Flow<Long>
}