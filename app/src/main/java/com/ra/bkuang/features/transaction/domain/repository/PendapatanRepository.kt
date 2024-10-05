package com.ra.bkuang.features.transaction.domain.repository

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.core.data.source.local.database.entity.DetailPendapatan
import com.ra.bkuang.features.transaction.domain.model.PendapatanModel
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import java.util.UUID

interface PendapatanRepository {
  suspend fun getTotalPendapatanByDate(fromDate: LocalDateTime, toDate: LocalDateTime): Long?
  suspend fun findById(uuid: UUID): PendapatanModel
  suspend fun findDetailById(uuid: UUID): DetailPendapatan
  fun getTotalPendapatanByDateWithFlow(fromDate: LocalDateTime, toDate: LocalDateTime): Flow<Long>
  fun getTotalPendapatanWithFlow(): Flow<Long?>
  fun getListDetailPendapatanByDate(fromDate: LocalDateTime, toDate: LocalDateTime): Flow<Result<List<DetailPendapatan>>>
  fun save(incomeModel: PendapatanModel): Flow<Result<Boolean>>
  fun delete(uuid: UUID): Flow<Result<Boolean>>
  fun update(newIncomeModel: PendapatanModel, oldIncomeModel: PendapatanModel): Flow<Result<Boolean>>
}