package com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.impl

import com.ra.bkuang.features.transaction.data.entity.DetailPengeluaran
import com.ra.bkuang.features.transaction.domain.PengeluaranRepository
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.GetListDetailPengeluaranByDate
import java.time.LocalDateTime
import javax.inject.Inject

class GetListDetailPengeluaranByDateImpl @Inject constructor(
  private val repository: PengeluaranRepository
): GetListDetailPengeluaranByDate {

  override suspend fun invoke(fromDate: LocalDateTime, toDate: LocalDateTime): List<DetailPengeluaran> {
    return repository.getListDetailPengeluaranByDate(fromDate, toDate)
  }
}