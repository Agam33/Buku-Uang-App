package com.ra.budgetplan.domain.usecase.transaksi.transfer

import com.ra.budgetplan.util.ResourceState
import java.util.UUID

interface DeleteTransferById {
  suspend fun invoke(uuid: UUID): ResourceState
}