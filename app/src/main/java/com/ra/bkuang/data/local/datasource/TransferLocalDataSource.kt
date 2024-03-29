package com.ra.bkuang.data.local.datasource

import com.ra.bkuang.data.local.database.entity.DetailTransfer
import com.ra.bkuang.data.local.database.entity.TransferEntity
import java.time.LocalDateTime
import java.util.UUID

interface TransferLocalDataSource {
  suspend fun findById(uuid: UUID): TransferEntity
  suspend fun findDetailById(uuid: UUID): DetailTransfer
  suspend fun getTransferByDate(fromDate: LocalDateTime, toDate: LocalDateTime): List<DetailTransfer>
  suspend fun saveTransfer(transfer: TransferEntity)
  suspend fun deleteTransfer(transfer: TransferEntity)
  suspend fun updateTransfer(transfer: TransferEntity)
}