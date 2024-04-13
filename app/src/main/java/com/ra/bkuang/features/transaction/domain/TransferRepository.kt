package com.ra.bkuang.features.transaction.domain

import com.ra.bkuang.features.transaction.data.entity.DetailTransfer
import com.ra.bkuang.features.transaction.data.entity.TransferEntity
import com.ra.bkuang.features.transaction.domain.model.TransferModel
import java.time.LocalDateTime
import java.util.UUID

interface TransferRepository {
  suspend fun findById(uuid: UUID): TransferModel
  suspend fun findDetailById(uuid: UUID): DetailTransfer
  suspend fun getTransferByDate(fromDate: LocalDateTime, toDate: LocalDateTime): List<DetailTransfer>
  suspend fun save(transfer: TransferModel)
  suspend fun delete(transfer: TransferModel)
  suspend fun update(transfer: TransferModel)
}