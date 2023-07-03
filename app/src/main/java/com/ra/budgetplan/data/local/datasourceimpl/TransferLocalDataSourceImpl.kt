package com.ra.budgetplan.data.local.datasourceimpl

import com.ra.budgetplan.data.local.TransferLocalDataSource
import com.ra.budgetplan.data.local.database.dao.TransferDao
import com.ra.budgetplan.domain.entity.DetailTransfer
import com.ra.budgetplan.domain.entity.TransferEntity
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

  override suspend fun getTransferByDate(
    fromDate: LocalDateTime, toDate: LocalDateTime
  ): List<DetailTransfer> {
    return transferDao.getTransferByDate(fromDate, toDate)
  }

  override suspend fun saveTransfer(transfer: TransferEntity) {
    return transferDao.save(transfer)
  }

  override suspend fun deleteTransfer(transfer: TransferEntity) {
    return transferDao.delete(transfer)
  }

  override suspend fun updateTransfer(transfer: TransferEntity) {
    return transferDao.update(transfer)
  }
}