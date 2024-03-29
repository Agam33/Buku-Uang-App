package com.ra.bkuang.data.local.datasource.impl

import com.ra.bkuang.data.local.datasource.HutangLocalDataSource
import com.ra.bkuang.data.local.database.dao.HutangDao
import com.ra.bkuang.data.local.database.entity.HutangEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class HutangLocalDataSourceImpl @Inject constructor(
  private val hutangDao: HutangDao
): HutangLocalDataSource {
  override suspend fun findByAlarmId(alarmId: Int): HutangEntity {
    return hutangDao.findByAlarmId(alarmId)
  }

  override suspend fun save(hutang: HutangEntity) {
    return hutangDao.save(hutang)
  }

  override suspend fun delete(hutang: HutangEntity) {
    return hutangDao.delete(hutang)
  }

  override suspend fun update(hutang: HutangEntity) {
    return hutangDao.update(hutang)
  }

  override suspend fun findById(id: UUID): HutangEntity {
    return hutangDao.findById(id)
  }

  override suspend fun findAll(): List<HutangEntity> {
    return hutangDao.findAll()
  }

  override fun findByIdWithFlow(id: UUID): Flow<HutangEntity> {
    return hutangDao.findByIdWithFlow(id)
  }
}