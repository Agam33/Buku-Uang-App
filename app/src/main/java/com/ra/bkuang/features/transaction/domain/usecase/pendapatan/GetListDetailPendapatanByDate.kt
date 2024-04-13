package com.ra.bkuang.features.transaction.domain.usecase.pendapatan

import com.ra.bkuang.features.transaction.data.entity.DetailPendapatan
import java.time.LocalDateTime

interface GetListDetailPendapatanByDate {
  suspend fun invoke(fromDate: LocalDateTime, toDate: LocalDateTime): List<DetailPendapatan>
}