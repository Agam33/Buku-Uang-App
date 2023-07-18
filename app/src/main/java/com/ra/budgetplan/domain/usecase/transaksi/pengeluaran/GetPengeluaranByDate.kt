package com.ra.budgetplan.domain.usecase.transaksi.pengeluaran

import com.ra.budgetplan.domain.entity.DetailPengeluaran
import com.ra.budgetplan.util.Resource
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface GetPengeluaranByDate {
  suspend fun invoke(fromDate: LocalDateTime, toDate: LocalDateTime): List<DetailPengeluaran>
}