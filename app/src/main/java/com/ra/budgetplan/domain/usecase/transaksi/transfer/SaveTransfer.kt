package com.ra.budgetplan.domain.usecase.transaksi.transfer

import com.ra.budgetplan.domain.model.TransferModel
import com.ra.budgetplan.util.ResourceState

interface SaveTransfer {
  suspend fun invoke(transferModel: TransferModel): ResourceState
}