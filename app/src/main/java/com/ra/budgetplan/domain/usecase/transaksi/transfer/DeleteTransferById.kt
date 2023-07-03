package com.ra.budgetplan.domain.usecase.transaksi.transfer

import java.util.UUID

interface DeleteTransferById {
  suspend fun invoke(uuid: UUID)
}