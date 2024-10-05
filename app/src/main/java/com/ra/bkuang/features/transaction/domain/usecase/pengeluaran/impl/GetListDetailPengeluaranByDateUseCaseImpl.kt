package com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.impl

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.core.data.source.local.database.entity.DetailPengeluaran
import com.ra.bkuang.features.transaction.domain.repository.PengeluaranRepository
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.GetListDetailPengeluaranByDateUseCase
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import javax.inject.Inject

class GetListDetailPengeluaranByDateUseCaseImpl @Inject constructor(
  private val repository: PengeluaranRepository
): GetListDetailPengeluaranByDateUseCase {

  override fun invoke(fromDate: LocalDateTime, toDate: LocalDateTime): Flow<Result<List<DetailPengeluaran>>> {
    return repository.getListDetailPengeluaranByDate(fromDate, toDate)
  }
}