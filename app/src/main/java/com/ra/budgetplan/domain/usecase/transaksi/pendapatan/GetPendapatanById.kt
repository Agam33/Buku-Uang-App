package com.ra.budgetplan.domain.usecase.transaksi.pendapatan

import com.ra.budgetplan.domain.model.PendapatanModel
import java.util.UUID

interface GetPendapatanById {
  suspend fun invoke(uuid: UUID): PendapatanModel
}