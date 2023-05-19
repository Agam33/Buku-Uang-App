package com.ra.budgetplan.domain.repository

import com.ra.budgetplan.domain.model.TransferModel

interface TransferRepository {
  suspend fun save(transferModel: TransferModel)
  suspend fun delete(transferModel: TransferModel)
  suspend fun update(transferModel: TransferModel)
}