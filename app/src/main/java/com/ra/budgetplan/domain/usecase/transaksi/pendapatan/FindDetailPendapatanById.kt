package com.ra.budgetplan.domain.usecase.transaksi.pendapatan

import com.ra.budgetplan.domain.entity.DetailPendapatan
import java.util.UUID

interface FindDetailPendapatanById {
  suspend fun invoke(id: UUID): DetailPendapatan
}