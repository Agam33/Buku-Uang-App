package com.ra.budgetplan.domain.usecase.transaksi.pengeluaran

import com.ra.budgetplan.util.ResourceState
import java.util.UUID

interface DeletePengeluaranById {
  suspend fun invoke(uuid: UUID): ResourceState
}