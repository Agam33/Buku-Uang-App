package com.ra.budgetplan.domain.repository

import com.ra.budgetplan.domain.entity.TransferEntity

interface TransferRepository {
  suspend fun save(transfer: TransferEntity)
  suspend fun delete(transfer: TransferEntity)
  suspend fun update(transfer: TransferEntity)
}