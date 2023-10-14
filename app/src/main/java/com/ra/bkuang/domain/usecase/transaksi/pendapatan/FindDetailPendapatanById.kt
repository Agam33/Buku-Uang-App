package com.ra.bkuang.domain.usecase.transaksi.pendapatan

import com.ra.bkuang.domain.entity.DetailPendapatan
import java.util.UUID

interface FindDetailPendapatanById {
  suspend fun invoke(id: UUID): DetailPendapatan
}