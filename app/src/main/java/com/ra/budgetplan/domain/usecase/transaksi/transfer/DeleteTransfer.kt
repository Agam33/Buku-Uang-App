package com.ra.budgetplan.domain.usecase.transaksi.transfer

import com.ra.budgetplan.domain.model.TransferModel

interface DeleteTransfer {
  suspend fun invoke(transferModel: TransferModel)
}