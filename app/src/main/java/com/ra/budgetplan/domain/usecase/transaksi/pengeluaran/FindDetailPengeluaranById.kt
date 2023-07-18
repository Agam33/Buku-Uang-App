package com.ra.budgetplan.domain.usecase.transaksi.pengeluaran

import com.ra.budgetplan.domain.entity.DetailPengeluaran
import java.util.UUID

interface FindDetailPengeluaranById {
  suspend fun invoke(id: UUID): DetailPengeluaran
}