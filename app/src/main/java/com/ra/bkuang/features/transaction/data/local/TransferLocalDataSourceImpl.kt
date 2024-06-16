package com.ra.bkuang.features.transaction.data.local

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.core.database.dao.TransferDao
import com.ra.bkuang.features.transaction.data.entity.DetailTransfer
import com.ra.bkuang.features.transaction.data.entity.TransferEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class TransferLocalDataSourceImpl @Inject constructor(
  private val transferDao: TransferDao
): TransferLocalDataSource {
  override suspend fun findById(uuid: UUID): TransferEntity {
    return transferDao.findById(uuid)
  }

  override suspend fun findDetailById(uuid: UUID): DetailTransfer {
    return transferDao.findDetailTransferById(uuid)
  }

  override fun getTransferByDate(
    fromDate: LocalDateTime, toDate: LocalDateTime
  ): Flow<List<DetailTransfer>> {
    return transferDao.getTransferByDate(fromDate, toDate)
  }

  override suspend fun save(transfer: TransferEntity) {
    return transferDao.save(transfer)
  }

  override suspend fun delete(transfer: TransferEntity) {
    return transferDao.delete(transfer)
  }

  override suspend fun update(transfer: TransferEntity) {
    return transferDao.update(transfer)
  }
}