package com.ra.bkuang.domain.usecase.transaksi.pengeluaran.impl

import com.ra.bkuang.domain.entity.DetailPengeluaran
import com.ra.bkuang.domain.repository.PengeluaranRepository
import com.ra.bkuang.domain.usecase.transaksi.pengeluaran.GetPengeluaranByDate
import java.time.LocalDateTime
import javax.inject.Inject

class GetPengeluaranByDateImpl @Inject constructor(
  private val repository: PengeluaranRepository
): GetPengeluaranByDate {

  override suspend fun invoke(fromDate: LocalDateTime, toDate: LocalDateTime): List<DetailPengeluaran> {
    return repository.getPengeluaranByDate(fromDate, toDate)
  }
}