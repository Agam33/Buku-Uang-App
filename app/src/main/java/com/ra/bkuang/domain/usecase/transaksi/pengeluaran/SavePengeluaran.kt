package com.ra.bkuang.domain.usecase.transaksi.pengeluaran

import com.ra.bkuang.domain.model.PengeluaranModel
import com.ra.bkuang.util.ResourceState

interface SavePengeluaran {
  suspend fun invoke(pengeluaranModel: PengeluaranModel): ResourceState
}