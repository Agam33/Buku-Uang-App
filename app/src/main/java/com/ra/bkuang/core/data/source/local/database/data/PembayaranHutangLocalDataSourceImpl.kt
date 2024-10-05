package com.ra.bkuang.core.data.source.local.database.data

import com.ra.bkuang.core.data.source.local.database.dao.PembayaranHutangDao
import com.ra.bkuang.core.data.source.local.database.entity.DetailPembayaranHutang
import com.ra.bkuang.core.data.source.local.database.entity.PembayaranHutangEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class PembayaranHutangLocalDataSourceImpl @Inject constructor(
  private val pembayaranHutangDao: PembayaranHutangDao
): PembayaranHutangLocalDataSource {
  override fun getSizeListPembayaranHutangById(id: UUID): Flow<Int?> {
    return pembayaranHutangDao.getSizeListPembayaranHutangById(id)
  }

  override fun findAllRecordByHutangId(id: UUID): Flow<List<DetailPembayaranHutang>> {
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