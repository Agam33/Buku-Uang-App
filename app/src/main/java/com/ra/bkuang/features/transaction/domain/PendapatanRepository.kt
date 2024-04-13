package com.ra.bkuang.features.transaction.domain

import com.ra.bkuang.features.transaction.data.entity.DetailPendapatan
import com.ra.bkuang.features.transaction.data.entity.PendapatanEntity
import com.ra.bkuang.features.transaction.domain.model.PendapatanModel
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import java.util.UUID

interface PendapatanRepository {
  suspend fun getTotalPendapatanByDate(fromDate: LocalDateTime, toDate: LocalDateTime): Long?
  suspend fun findById(uuid: UUID): PendapatanModel
  suspend fun findDetailById(uuid: UUID): DetailPendapatan
  fun getTotalPendapatanByDateWithFlow(fromDate: LocalDateTime, toDate: LocalDateTime): Flow<Long?>
  fun getTotalPendapatanWithFlow(): Flow<Long?>
  suspend fun getListDetailPendapatanByDate(fromDate: LocalDateTime, toDate: LocalDateTime): List<DetailPendapatan>
  suspend fun save(pendapatan: PendapatanModel)
  suspend fun delete(pendapatan: PendapatanModel)
  suspend fun update(pendapatan: PendapatanModel)
}