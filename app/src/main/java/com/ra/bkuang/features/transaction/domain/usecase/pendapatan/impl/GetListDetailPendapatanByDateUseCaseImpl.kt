package com.ra.bkuang.features.transaction.domain.usecase.pendapatan.impl

import com.ra.bkuang.features.transaction.data.entity.DetailPendapatan
import com.ra.bkuang.features.transaction.domain.PendapatanRepository
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.GetListDetailPendapatanByDateUseCase
import java.time.LocalDateTime
import javax.inject.Inject

class GetListDetailPendapatanByDateUseCaseImpl @Inject constructor(
  private val repository: PendapatanRepository
): GetListDetailPendapatanByDateUseCase {
  override suspend fun invoke(fromDate: LocalDateTime, toDate: LocalDateTime): List<DetailPendapatan> {
    return repository.getListDetailPendapatanByDate(fromDate, toDate)
  }
}