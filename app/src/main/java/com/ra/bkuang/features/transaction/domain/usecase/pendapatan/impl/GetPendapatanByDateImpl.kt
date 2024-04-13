package com.ra.bkuang.features.transaction.domain.usecase.pendapatan.impl
import com.ra.bkuang.features.transaction.data.entity.DetailPendapatan
import com.ra.bkuang.features.transaction.domain.PendapatanRepository
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.GetPendapatanByDate
import java.time.LocalDateTime
import javax.inject.Inject

class GetPendapatanByDateImpl @Inject constructor(
  private val repository: PendapatanRepository
): GetPendapatanByDate {
  override suspend fun invoke(fromDate: LocalDateTime, toDate: LocalDateTime): List<DetailPendapatan> {
    return repository.getPendapatanByDate(fromDate, toDate)
  }
}