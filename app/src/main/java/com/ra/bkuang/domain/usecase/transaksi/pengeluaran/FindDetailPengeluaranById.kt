package com.ra.bkuang.domain.usecase.transaksi.pengeluaran

import com.ra.bkuang.data.local.database.entity.DetailPengeluaran
import java.util.UUID

interface FindDetailPengeluaranById {
  suspend fun invoke(id: UUID): DetailPengeluaran
}