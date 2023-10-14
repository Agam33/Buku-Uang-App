package com.ra.budgetplan.domain.usecase.transaksi.pendapatan

import com.ra.budgetplan.util.ResourceState
import java.util.UUID

interface DeletePendapatanById {
  suspend fun invoke(uuid: UUID): ResourceState
}