package com.ra.budgetplan.domain.usecase.transaksi.transfer

import com.ra.budgetplan.domain.model.TransferModel
import com.ra.budgetplan.util.ResourceState

interface UpdateTransfer {
  suspend fun invoke(newTransferModel: TransferModel, oldTransferModel: TransferModel): ResourceState
}