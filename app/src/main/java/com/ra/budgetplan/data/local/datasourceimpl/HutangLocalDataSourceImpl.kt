package com.ra.budgetplan.data.local.datasourceimpl

import com.ra.budgetplan.data.local.HutangLocalDataSource
import com.ra.budgetplan.data.local.database.dao.HutangDao
import com.ra.budgetplan.domain.entity.HutangEntity
import javax.inject.Inject

class HutangLocalDataSourceImpl @Inject constructor(
  private val hutangDao: HutangDao
): HutangLocalDataSource {
  override suspend fun save(hutang: HutangEntity) {
    return hutangDao.save(hutang)
  }

  override suspend fun delete(hutang: HutangEntity) {
    return hutangDao.delete(hutang)
  }

  override suspend fun update(hutang: HutangEntity) {
    return hutangDao.update(hutang)
  }
}