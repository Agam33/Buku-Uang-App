package com.ra.budgetplan.domain.repository

import com.ra.budgetplan.domain.entity.DetailTransfer
import com.ra.budgetplan.domain.entity.TransferEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface TransferRepository {
  fun getTransferByDate(fromDate: LocalDateTime, toDate: LocalDateTime): Flow<List<DetailTransfer>>
  suspend fun save(transfer: TransferEntity)
  suspend fun delete(transfer: TransferEntity)
  suspend fun update(transfer: TransferEntity)
}