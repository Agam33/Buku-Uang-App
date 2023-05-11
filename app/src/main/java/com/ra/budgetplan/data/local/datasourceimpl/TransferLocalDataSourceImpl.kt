package com.ra.budgetplan.data.local.datasourceimpl

import com.ra.budgetplan.data.local.TransferLocalDataSource
import com.ra.budgetplan.data.local.database.dao.TransferDao
import com.ra.budgetplan.domain.entity.TransferEntity
import javax.inject.Inject

class TransferLocalDataSourceImpl @Inject constructor(
  private val transferDao: TransferDao
): TransferLocalDataSource {

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