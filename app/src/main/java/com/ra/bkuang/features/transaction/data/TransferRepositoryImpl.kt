package com.ra.bkuang.features.transaction.data

import com.ra.bkuang.features.transaction.data.entity.DetailTransfer
import com.ra.bkuang.features.transaction.data.local.TransferLocalDataSource
import com.ra.bkuang.features.transaction.data.mapper.toEntity
import com.ra.bkuang.features.transaction.data.mapper.toModel
import com.ra.bkuang.features.transaction.domain.TransferRepository
import com.ra.bkuang.features.transaction.domain.model.TransferModel
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class TransferRepositoryImpl @Inject constructor(
  private val localDataSource: TransferLocalDataSource
): TransferRepository {
  override suspend fun findById(uuid: UUID): TransferModel {
    return localDataSource.findById(uuid).toModel()
  }

  override suspend fun findDetailById(uuid: UUID): DetailTransfer {
    return localDataSource.findDetailById(uuid)
  }

  override suspend fun getTransferByDate(
    fromDate: LocalDateTime, toDate: LocalDateTime
  ): List<DetailTransfer> {
    return localDataSource.getTransferByDate(fromDate, toDate)
  }

  override suspend fun save(transfer: TransferModel) {
    return localDataSource.saveTransfer(transfer.toEntity())
  }

  override suspend fun delete(transfer: TransferModel) {
    return localDataSource.deleteTransfer(transfer.toEntity())
  }

  override suspend fun update(transfer: TransferModel) {
    return localDataSource.updateTransfer(transfer.toEntity())
  }
}