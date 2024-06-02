package com.ra.bkuang.features.debt.domain.usecase

import com.ra.bkuang.features.debt.domain.model.PembayaranHutangModel

interface CreatePembayaranHutangUseCase {
  suspend fun invoke(pembayaranHutangModel: PembayaranHutangModel)
}