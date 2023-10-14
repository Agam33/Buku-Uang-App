package com.ra.bkuang.domain.usecase.transaksi.pendapatan

import com.ra.bkuang.domain.model.PendapatanModel
import java.util.UUID

interface GetPendapatanById {
  suspend fun invoke(uuid: UUID): PendapatanModel
}