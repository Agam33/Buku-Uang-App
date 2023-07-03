package com.ra.budgetplan.domain.usecase.transaksi.transfer

import com.ra.budgetplan.domain.entity.DetailTransfer
import java.util.UUID

interface FindDetailTransferById {
  suspend fun invoke(id: UUID): DetailTransfer
}