package com.ra.budgetplan.domain.usecase.transaksi.transfer

import com.ra.budgetplan.domain.model.TransferModel

interface SaveTransfer {
  suspend fun invoke(transferModel: TransferModel)
}