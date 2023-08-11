package com.ra.budgetplan.data.repositoryimpl

import com.ra.budgetplan.data.local.PembayaranHutangLocalDataSource
import com.ra.budgetplan.domain.entity.DetailPembayaranHutang
import com.ra.budgetplan.domain.entity.PembayaranHutangEntity
import com.ra.budgetplan.domain.repository.PembayaranHutangRepository
import java.util.UUID
import javax.inject.Inject

class PembayaranHutangRepositoryImpl @Inject constructor(
  private val localDataSource: PembayaranHutangLocalDataSource
): PembayaranHutangRepository {
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