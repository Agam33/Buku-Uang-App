package com.ra.budgetplan.domain.usecase.hutang

import com.ra.budgetplan.domain.model.PembayaranHutangModel

interface CreatePembayaranHutang {
  suspend fun invoke(pembayaranHutangModel: PembayaranHutangModel)
}