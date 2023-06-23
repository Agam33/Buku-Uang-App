package com.ra.budgetplan.domain.usecase.transaksi.pendapatan

import com.ra.budgetplan.domain.entity.DetailPendapatan
import com.ra.budgetplan.util.Resource
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface GetPendapatanByDate {
  suspend fun invoke(fromDate: LocalDateTime, toDate: LocalDateTime): List<DetailPendapatan>
}