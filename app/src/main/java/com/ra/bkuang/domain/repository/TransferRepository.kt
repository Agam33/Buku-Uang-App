package com.ra.bkuang.domain.repository

import com.ra.bkuang.data.local.entity.DetailTransfer
import com.ra.bkuang.data.local.entity.TransferEntity
import java.time.LocalDateTime
import java.util.UUID

interface TransferRepository {
  suspend fun findById(uuid: UUID): TransferEntity
  suspend fun findDetailById(uuid: UUID): DetailTransfer
  suspend fun getTransferByDate(fromDate: LocalDateTime, toDate: LocalDateTime): List<DetailTransfer>
  suspend fun save(transfer: TransferEntity)
  suspend fun delete(transfer: TransferEntity)
  suspend fun update(transfer: TransferEntity)
}