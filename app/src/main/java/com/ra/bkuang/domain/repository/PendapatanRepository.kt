package com.ra.bkuang.domain.repository

import com.ra.bkuang.data.local.database.entity.DetailPendapatan
import com.ra.bkuang.data.local.database.entity.PendapatanEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import java.util.UUID

interface PendapatanRepository {
  suspend fun getTotalPendapatan(fromDate: LocalDateTime, toDate: LocalDateTime): Long?
  suspend fun findById(uuid: UUID): PendapatanEntity
  suspend fun findDetailById(uuid: UUID): DetailPendapatan
  fun getTotalPendapatanByDate(fromDate: LocalDateTime, toDate: LocalDateTime): Flow<Long?>
  fun getTotalPendapatan(): Flow<Long?>
  suspend fun getPendapatanByDate(fromDate: LocalDateTime, toDate: LocalDateTime): List<DetailPendapatan>
  suspend fun save(pendapatan: PendapatanEntity)
  suspend fun delete(pendapatan: PendapatanEntity)
  suspend fun update(pendapatan: PendapatanEntity)
}