package com.ra.bkuang.data.repository

import com.ra.bkuang.data.local.datasource.TransferLocalDataSource
import com.ra.bkuang.data.local.entity.DetailTransfer
import com.ra.bkuang.data.local.entity.TransferEntity
import com.ra.bkuang.domain.repository.TransferRepository
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class TransferRepositoryImpl @Inject constructor(
  private val localDataSource: TransferLocalDataSource
): TransferRepository {
  override suspend fun findById(uuid: UUID): TransferEntity {
    return localDataSource.findById(uuid)
  }

  override suspend fun findDetailById(uuid: UUID): DetailTransfer {
    return localDataSource.findDetailById(uuid)
  }

  override suspend fun getTransferByDate(
    fromDate: LocalDateTime, toDate: LocalDateTime
  ): List<DetailTransfer> {
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