package com.ra.bkuang.domain.usecase.transaksi.pengeluaran

import com.ra.bkuang.data.local.entity.DetailPengeluaran
import java.time.LocalDateTime

interface GetPengeluaranByDate {
  suspend fun invoke(fromDate: LocalDateTime, toDate: LocalDateTime): List<DetailPengeluaran>
}