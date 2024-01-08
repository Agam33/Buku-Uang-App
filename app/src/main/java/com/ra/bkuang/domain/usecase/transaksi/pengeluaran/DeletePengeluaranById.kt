package com.ra.bkuang.domain.usecase.transaksi.pengeluaran

import com.ra.bkuang.domain.util.ResourceState
import java.util.UUID

interface DeletePengeluaranById {
  suspend fun invoke(uuid: UUID): ResourceState
}