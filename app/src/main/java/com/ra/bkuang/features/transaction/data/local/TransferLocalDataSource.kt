package com.ra.bkuang.features.transaction.data.local

import com.ra.bkuang.core.data.source.local.database.entity.DetailTransfer
import com.ra.bkuang.core.data.source.local.database.entity.TransferEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import java.util.UUID

interface TransferLocalDataSource {
  suspend fun findById(uuid: UUID): TransferEntity
  suspend fun findDetailById(uuid: UUID): DetailTransfer
  fun getTransferByDate(fromDate: LocalDateTime, toDate: LocalDateTime): Flow<List<DetailTransfer>>
  suspend fun save(transfer: TransferEntity)
  suspend fun delete(transfer: TransferEntity)
  suspend fun update(transfer: TransferEntity)
}