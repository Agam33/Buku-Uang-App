package com.ra.bkuang.domain.usecase.transaksi.pendapatan

import com.ra.bkuang.data.entity.DetailPendapatan
import java.time.LocalDateTime

interface GetPendapatanByDate {
  suspend fun invoke(fromDate: LocalDateTime, toDate: LocalDateTime): List<DetailPendapatan>
}