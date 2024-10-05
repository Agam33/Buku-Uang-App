package com.ra.bkuang.features.transaction.domain.usecase.pengeluaran

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.core.data.source.local.database.entity.DetailPengeluaran
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface GetListDetailPengeluaranByDateUseCase {
  operator fun invoke(fromDate: LocalDateTime, toDate: LocalDateTime): Flow<Result<List<DetailPengeluaran>>>
}