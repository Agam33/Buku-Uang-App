package com.ra.budgetplan.data.local.datasourceimpl

import com.ra.budgetplan.data.local.TransferLocalDataSource
import com.ra.budgetplan.data.local.database.dao.TransferDao
import com.ra.budgetplan.domain.entity.DetailTransfer
import com.ra.budgetplan.domain.entity.TransferEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import javax.inject.Inject

class TransferLocalDataSourceImpl @Inject constructor(
  private val transferDao: TransferDao
): TransferLocalDataSource {
  override fun getTransferByDate(
    fromDate: LocalDateTime, toDate: LocalDateTime
  ): Flow<List<DetailTransfer>> {
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