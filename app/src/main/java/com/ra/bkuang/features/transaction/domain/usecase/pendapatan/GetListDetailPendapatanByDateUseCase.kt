package com.ra.bkuang.features.transaction.domain.usecase.pendapatan

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.transaction.data.entity.DetailPendapatan
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface GetListDetailPendapatanByDateUseCase {
  operator fun invoke(fromDate: LocalDateTime, toDate: LocalDateTime): Flow<Result<List<DetailPendapatan>>>
}