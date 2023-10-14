package com.ra.bkuang.domain.usecase.hutang.impl

import com.ra.bkuang.domain.mapper.toEntity
import com.ra.bkuang.domain.model.PembayaranHutangModel
import com.ra.bkuang.domain.repository.PembayaranHutangRepository
import com.ra.bkuang.domain.usecase.hutang.CreatePembayaranHutang
import javax.inject.Inject

class CreatePembayaranHutangImpl @Inject constructor(
  private val pembayaranHutangRepository: PembayaranHutangRepository
): CreatePembayaranHutang {
  override suspend fun invoke(pembayaranHutangModel: PembayaranHutangModel) {
    pembayaranHutangRepository.save(pembayaranHutangModel.toEntity())
  }
}