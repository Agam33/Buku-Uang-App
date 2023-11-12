package com.ra.bkuang.domain.usecase.transaksi.pendapatan.impl

import com.ra.bkuang.data.local.entity.DetailPendapatan
import com.ra.bkuang.domain.repository.PendapatanRepository
import com.ra.bkuang.domain.usecase.transaksi.pendapatan.GetPendapatanByDate
import java.time.LocalDateTime
import javax.inject.Inject

class GetPendapatanByDateImpl @Inject constructor(
  private val repository: PendapatanRepository
): GetPendapatanByDate {
  override suspend fun invoke(fromDate: LocalDateTime, toDate: LocalDateTime): List<DetailPendapatan> {
    return repository.getPendapatanByDate(fromDate, toDate)
  }
}