package com.ra.bkuang.data.repository

import com.ra.bkuang.data.local.datasource.PembayaranHutangLocalDataSource
import com.ra.bkuang.data.local.database.entity.DetailPembayaranHutang
import com.ra.bkuang.data.local.database.entity.PembayaranHutangEntity
import com.ra.bkuang.domain.repository.PembayaranHutangRepository
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class PembayaranHutangRepositoryImpl @Inject constructor(
  private val localDataSource: PembayaranHutangLocalDataSource
): PembayaranHutangRepository {
  override fun getSizeListPembayaranHutangById(id: UUID): Flow<Int?> {
    return localDataSource.getSizeListPembayaranHutangById(id)
  }

  override suspend fun findAllRecordByHutangId(id: UUID): List<DetailPembayaranHutang> {
    return localDataSource.findAllRecordByHutangId(id)
  }

  override suspend fun save(pembayaranHutang: PembayaranHutangEntity) {
    localDataSource.save(pembayaranHutang)
  }

  override suspend fun delete(pembayaranHutang: PembayaranHutangEntity) {
    localDataSource.delete(pembayaranHutang)
  }

  override suspend fun update(pembayaranHutang: PembayaranHutangEntity) {
    localDataSource.update(pembayaranHutang)
  }
}