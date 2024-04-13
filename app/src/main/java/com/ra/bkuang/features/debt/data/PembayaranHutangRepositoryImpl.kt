package com.ra.bkuang.features.debt.data

import com.ra.bkuang.features.debt.data.entity.DetailPembayaranHutang
import com.ra.bkuang.features.debt.data.local.PembayaranHutangLocalDataSource
import com.ra.bkuang.features.debt.data.mapper.toEntity
import com.ra.bkuang.features.debt.domain.PembayaranHutangRepository
import com.ra.bkuang.features.debt.domain.model.PembayaranHutangModel
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

  override suspend fun save(pembayaranHutang: PembayaranHutangModel) {
    localDataSource.save(pembayaranHutang.toEntity())
  }

  override suspend fun delete(pembayaranHutang: PembayaranHutangModel) {
    localDataSource.delete(pembayaranHutang.toEntity())
  }

  override suspend fun update(pembayaranHutang: PembayaranHutangModel) {
    localDataSource.update(pembayaranHutang.toEntity())
  }
}