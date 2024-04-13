package com.ra.bkuang.features.transaction.domain.usecase.pendapatan.impl

import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.transaction.data.entity.DetailPendapatan
import com.ra.bkuang.features.transaction.domain.PendapatanRepository
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.GetListDetailPendapatanByDate
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject

class GetListDetailPendapatanByDateImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispather: CoroutineDispatcher,
  private val repository: PendapatanRepository
): GetListDetailPendapatanByDate {
  override suspend fun invoke(fromDate: LocalDateTime, toDate: LocalDateTime): List<DetailPendapatan> = withContext(ioDispather) {
    return@withContext repository.getListDetailPendapatanByDate(fromDate, toDate)
  }
}