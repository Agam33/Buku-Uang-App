package com.ra.bkuang.features.transaction.data

import com.ra.bkuang.features.transaction.data.entity.DetailPengeluaran
import com.ra.bkuang.features.transaction.data.local.PengeluaranLocalDataSource
import com.ra.bkuang.features.transaction.data.mapper.toEntity
import com.ra.bkuang.features.transaction.data.mapper.toModel
import com.ra.bkuang.features.transaction.domain.PengeluaranRepository
import com.ra.bkuang.features.transaction.domain.model.PengeluaranModel
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class PengeluaranRepositoryImpl @Inject constructor(
  private val localDataSource: PengeluaranLocalDataSource
): PengeluaranRepository {
  override suspend fun getTotalPengeluaranByDateAndKategory(
    fromDate: LocalDateTime,
    toDate: LocalDateTime,
    idKategori: UUID
  ): Long? {
    return localDataSource.getTotalPengeluaranByDateAndKategory(fromDate, toDate, idKategori)
  }

  override suspend fun getTotalPengeluaranByDate(fromDate: LocalDateTime, toDate: LocalDateTime): Long? {
    return localDataSource.getTotalPengeluaran(fromDate, toDate)
  }

  override suspend fun findById(uuid: UUID): PengeluaranModel {
    return localDataSource.findById(uuid).toModel()
  }

  override suspend fun findDetailById(uuid: UUID): DetailPengeluaran {
    return localDataSource.findDetailById(uuid)
  }

  override fun getTotalPengeluaranByDateWithFlow(
    fromDate: LocalDateTime,
    toDate: LocalDateTime
  ): Flow<Long?> {
    return localDataSource.getTotalPengeluaranByDate(fromDate, toDate)
  }

  override fun getTotalPengeluaranWithFlow(): Flow<Long?> {
    return localDataSource.getTotalPengeluaran()
  }

  override fun getMonthlyPengeluaran(startOfDay: LocalDateTime, endOfDay: LocalDateTime): Flow<List<DetailPengeluaran>> {
    return localDataSource.getMonthlyPengeluaran(startOfDay, endOfDay)
  }

  override suspend fun getListDetailPengeluaranByDate(
    fromDate: LocalDateTime, toDate: LocalDateTime
  ): List<DetailPengeluaran> {
    return localDataSource.getPengeluaranByDate(fromDate, toDate)
  }

  override suspend fun save(pengeluaran: PengeluaranModel) {
    return localDataSource.savePengeluaran(pengeluaran.toEntity())
  }

  override suspend fun delete(pengeluaran: PengeluaranModel) {
    return localDataSource.deletePengeluaran(pengeluaran.toEntity())
  }

  override suspend fun update(pengeluaran: PengeluaranModel) {
    return localDataSource.updatePengeluaran(pengeluaran.toEntity())
  }
}