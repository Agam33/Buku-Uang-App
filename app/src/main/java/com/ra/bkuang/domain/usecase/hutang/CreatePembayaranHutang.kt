package com.ra.bkuang.domain.usecase.hutang

import com.ra.bkuang.domain.model.PembayaranHutangModel

interface CreatePembayaranHutang {
  suspend fun invoke(pembayaranHutangModel: PembayaranHutangModel)
}