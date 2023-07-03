package com.ra.budgetplan.domain.usecase.transaksi.transfer

import com.ra.budgetplan.domain.model.TransferModel
import java.util.UUID

interface GetTransferById {
  suspend fun invoke(uuid: UUID): TransferModel
}