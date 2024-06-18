package com.ra.bkuang.features.transaction.domain.usecase.pendapatan.impl

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.transaction.data.entity.DetailPendapatan
import com.ra.bkuang.features.transaction.domain.repository.PendapatanRepository
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.GetListDetailPendapatanByDateUseCase
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import javax.inject.Inject

class GetListDetailPendapatanByDateUseCaseImpl @Inject constructor(
  private val repository: PendapatanRepository
): GetListDetailPendapatanByDateUseCase {
  override fun invoke(fromDate: LocalDateTime, toDate: LocalDateTime): Flow<Result<List<DetailPendapatan>>> {
    return repository.getListDetailPendapatanByDate(fromDate, toDate)
  }
}