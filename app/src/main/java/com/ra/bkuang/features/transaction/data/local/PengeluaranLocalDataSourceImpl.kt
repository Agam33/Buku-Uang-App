package com.ra.bkuang.features.transaction.data.local

import com.ra.bkuang.core.database.dao.PengeluaranDao
import com.ra.bkuang.features.transaction.data.entity.DetailPengeluaran
import com.ra.bkuang.features.transaction.data.entity.PengeluaranEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class PengeluaranLocalDataSourceImpl @Inject constructor(
  private val pengeluaranDao: PengeluaranDao
): PengeluaranLocalDataSource {
  override suspend fun getTotalPengeluaranByDateAndKategory(
    fromDate: LocalDateTime,
    toDate: LocalDateTime,
    id: UUID
  ): Long? {
    return pengeluaranDao.getTotalPengeluaranByDateAndKategori(fromDate, toDate, id)
  }

  override suspend fun getTotalPengeluaran(fromDate: LocalDateTime, toDate: LocalDateTime): Long? {
    return pengeluaranDao.getTotalPengeluaran(fromDate, toDate)
  }

  override suspend fun findById(uuid: UUID): PengeluaranEntity {
    return pengeluaranDao.findById(uuid)
  }

  override suspend fun findDetailById(uuid: UUID): DetailPengeluaran {
    return pengeluaranDao.findDetailPengeluaranById(uuid)
  }

  override fun getTotalPengeluaranByDate(
    fromDate: LocalDateTime,
    toDate: LocalDateTime
  ): Flow<Long?> {
    return pengeluaranDao.getTotalPengeluaranByDate(fromDate, toDate)
  }

  override fun getTotalPengeluaran(): Flow<Long?> {
    return pengeluaranDao.getTotalPengeluaran()
  }

  override fun getMonthlyPengeluaran(startOfDay: LocalDateTime, endOfDay: LocalDateTime): Flow<List<DetailPengeluaran>> {
    return pengeluaranDao.getMonthlyPengeluaran(startOfDay, endOfDay)
  }

  override suspend fun getPengeluaranByDate(
    fromDate: LocalDateTime, toDate: LocalDateTime
  ): List<DetailPengeluaran> {
    return pengeluaranDao.getPengeluaranByDate(fromDate, toDate)
  }

  override suspend fun savePengeluaran(pengeluaran: PengeluaranEntity) {
    return pengeluaranDao.save(pengeluaran)
  }

  override suspend fun deletePengeluaran(pengeluaran: PengeluaranEntity) {
    return pengeluaranDao.delete(pengeluaran)
  }

  override suspend fun updatePengeluaran(pengeluaran: PengeluaranEntity) {
    return pengeluaranDao.update(pengeluaran)
  }
}