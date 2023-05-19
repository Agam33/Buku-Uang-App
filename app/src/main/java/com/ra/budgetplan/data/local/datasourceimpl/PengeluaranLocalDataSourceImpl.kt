package com.ra.budgetplan.data.local.datasourceimpl

import com.ra.budgetplan.data.local.PengeluaranLocalDataSource
import com.ra.budgetplan.data.local.database.dao.PengeluaranDao
import com.ra.budgetplan.domain.entity.PengeluaranEntity
import javax.inject.Inject

class PengeluaranLocalDataSourceImpl @Inject constructor(
  private val pengeluaranDao: PengeluaranDao
): PengeluaranLocalDataSource {
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