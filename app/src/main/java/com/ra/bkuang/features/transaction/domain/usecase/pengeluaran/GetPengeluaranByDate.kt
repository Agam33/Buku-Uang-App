package com.ra.bkuang.features.transaction.domain.usecase.pengeluaran

import com.ra.bkuang.features.transaction.data.entity.DetailPengeluaran
import java.time.LocalDateTime

interface GetPengeluaranByDate {
  suspend fun invoke(fromDate: LocalDateTime, toDate: LocalDateTime): List<DetailPengeluaran>
}