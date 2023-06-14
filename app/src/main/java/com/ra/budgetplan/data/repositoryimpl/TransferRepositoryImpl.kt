package com.ra.budgetplan.data.repositoryimpl

import com.ra.budgetplan.data.local.TransferLocalDataSource
import com.ra.budgetplan.domain.entity.DetailTransfer
import com.ra.budgetplan.domain.entity.TransferEntity
import com.ra.budgetplan.domain.repository.TransferRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import javax.inject.Inject

class TransferRepositoryImpl @Inject constructor(
  private val localDataSource: TransferLocalDataSource
): TransferRepository {
  override fun getTransferByDate(
    fromDate: LocalDateTime, toDate: LocalDateTime
  ): Flow<List<DetailTransfer>> {
    return localDataSource.getTransferByDate(fromDate, toDate)
  }

  override suspend fun save(transfer: TransferEntity) {
    return localDataSource.saveTransfer(transfer)
  }

  override suspend fun delete(transfer: TransferEntity) {
    return localDataSource.deleteTransfer(transfer)
  }

  override suspend fun update(transfer: TransferEntity) {
    return localDataSource.updateTransfer(transfer)
  }
}