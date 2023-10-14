package com.ra.bkuang.domain.usecase.transaksi.pengeluaran

import com.ra.bkuang.domain.model.PengeluaranModel
import java.util.UUID

interface GetPengeluaranById {
  suspend fun invoke(uuid: UUID): PengeluaranModel
}