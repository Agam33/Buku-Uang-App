package com.ra.budgetplan.data.local

import com.ra.budgetplan.domain.entity.TransferEntity

interface TransferLocalDataSource {
  suspend fun saveTransfer(transfer: TransferEntity)
  suspend fun deleteTransfer(transfer: TransferEntity)
  suspend fun updateTransfer(transfer: TransferEntity)
}