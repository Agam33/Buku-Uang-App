package com.ra.budgetplan.domain.usecase.transaksi.pengeluaran.impl

import com.ra.budgetplan.domain.entity.DetailPengeluaran
import com.ra.budgetplan.domain.repository.PengeluaranRepository
import com.ra.budgetplan.domain.usecase.transaksi.pengeluaran.GetPengeluaranByDate
import com.ra.budgetplan.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.time.LocalDateTime
import javax.inject.Inject

class GetPengeluaranByDateImpl @Inject constructor(
  private val repository: PengeluaranRepository
): GetPengeluaranByDate {

  override suspend fun invoke(fromDate: LocalDateTime, toDate: LocalDateTime): List<DetailPengeluaran> {
    return repository.getPengeluaranByDate(fromDate, toDate)
  }
}