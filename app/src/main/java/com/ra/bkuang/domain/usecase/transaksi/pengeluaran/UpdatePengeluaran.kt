package com.ra.bkuang.domain.usecase.transaksi.pengeluaran

import com.ra.bkuang.domain.model.PengeluaranModel
import com.ra.bkuang.domain.util.ResourceState

interface UpdatePengeluaran {
  suspend fun invoke(newPengeluaranModel: PengeluaranModel, oldPengeluaranModel: PengeluaranModel): ResourceState
}