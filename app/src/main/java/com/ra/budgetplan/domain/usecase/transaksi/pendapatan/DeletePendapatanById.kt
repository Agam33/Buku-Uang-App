package com.ra.budgetplan.domain.usecase.transaksi.pendapatan

import java.util.UUID

interface DeletePendapatanById {
  suspend fun invoke(uuid: UUID)
}