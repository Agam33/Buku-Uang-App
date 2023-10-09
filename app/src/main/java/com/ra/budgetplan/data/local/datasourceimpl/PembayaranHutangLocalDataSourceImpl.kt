package com.ra.budgetplan.data.local.datasourceimpl

import com.ra.budgetplan.data.local.PembayaranHutangLocalDataSource
import com.ra.budgetplan.data.local.database.dao.PembayaranHutangDao
import com.ra.budgetplan.domain.entity.DetailPembayaranHutang
import com.ra.budgetplan.domain.entity.PembayaranHutangEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class PembayaranHutangLocalDataSourceImpl @Inject constructor(
  private val pembayaranHutangDao: PembayaranHutangDao
): PembayaranHutangLocalDataSource {
  override fun getSizeListPembayaranHutangById(id: UUID): Flow<Int?> {
    return pembayaranHutangDao.getSizeListPembayaranHutangById(id)
  }

  override suspend fun findAllRecordByHutangId(id: UUID): List<DetailPembayaranHutang> {
    return pembayaranHutangDao.findAllRecordByHutangId(id)
  }

  override suspend fun save(pembayaranHutang: PembayaranHutangEntity) {
    return pembayaranHutangDao.save(pembayaranHutang)
  }

  override suspend fun delete(pembayaranHutang: PembayaranHutangEntity) {
    return pembayaranHutangDao.delete(pembayaranHutang)
  }

  override suspend fun update(pembayaranHutang: PembayaranHutangEntity) {
    return pembayaranHutangDao.update(pembayaranHutang)
  }
}