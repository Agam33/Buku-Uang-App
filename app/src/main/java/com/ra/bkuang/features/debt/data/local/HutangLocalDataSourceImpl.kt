package com.ra.bkuang.features.debt.data.local

import com.ra.bkuang.core.database.dao.HutangDao
import com.ra.bkuang.features.debt.data.entity.HutangEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class HutangLocalDataSourceImpl @Inject constructor(
  private val hutangDao: HutangDao,
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

  override fun findAllWithFlow(): Flow<List<HutangEntity>> {
    return hutangDao.findAllWithFlow()
  }

  override fun findByIdWithFlow(id: UUID): Flow<HutangEntity?> {
    return hutangDao.findByIdWithFlow(id)
  }
}