package com.ra.budgetplan.data.local

import com.ra.budgetplan.domain.entity.DetailTransfer
import com.ra.budgetplan.domain.entity.TransferEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface TransferLocalDataSource {
  suspend fun getTransferByDate(fromDate: LocalDateTime, toDate: LocalDateTime): List<DetailTransfer>
  suspend fun saveTransfer(transfer: TransferEntity)
  suspend fun deleteTransfer(transfer: TransferEntity)
  suspend fun updateTransfer(transfer: TransferEntity)
}