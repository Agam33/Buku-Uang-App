package com.ra.bkuang.features.transaction.domain.usecase.pendapatan

import com.ra.bkuang.features.transaction.data.entity.DetailPendapatan
import java.time.LocalDateTime

interface GetListDetailPendapatanByDateUseCase {
  suspend fun invoke(fromDate: LocalDateTime, toDate: LocalDateTime): List<DetailPendapatan>
}