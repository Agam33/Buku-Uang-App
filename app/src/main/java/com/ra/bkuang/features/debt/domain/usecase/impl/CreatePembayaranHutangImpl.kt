package com.ra.bkuang.features.debt.domain.usecase.impl

import com.ra.bkuang.features.debt.data.mapper.toEntity
import com.ra.bkuang.features.debt.domain.PembayaranHutangRepository
import com.ra.bkuang.features.debt.domain.model.PembayaranHutangModel
import com.ra.bkuang.features.debt.domain.usecase.CreatePembayaranHutang
import javax.inject.Inject

class CreatePembayaranHutangImpl @Inject constructor(
  private val pembayaranHutangRepository: PembayaranHutangRepository
): CreatePembayaranHutang {
  override suspend fun invoke(pembayaranHutangModel: PembayaranHutangModel) {
    pembayaranHutangRepository.save(pembayaranHutangModel.toEntity())
  }
}