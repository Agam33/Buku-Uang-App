package com.ra.bkuang.domain.usecase.transaksi.pendapatan

import com.ra.bkuang.domain.util.ResourceState
import java.util.UUID

interface DeletePendapatanById {
  suspend fun invoke(uuid: UUID): ResourceState
}