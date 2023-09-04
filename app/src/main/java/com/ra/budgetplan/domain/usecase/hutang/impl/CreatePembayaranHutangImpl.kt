package com.ra.budgetplan.domain.usecase.hutang.impl

import com.ra.budgetplan.domain.mapper.toEntity
import com.ra.budgetplan.domain.model.PembayaranHutangModel
import com.ra.budgetplan.domain.repository.PembayaranHutangRepository
import com.ra.budgetplan.domain.usecase.hutang.CreatePembayaranHutang
import javax.inject.Inject

class CreatePembayaranHutangImpl @Inject constructor(
  private val pembayaranHutangRepository: PembayaranHutangRepository
): CreatePembayaranHutang {
  override suspend fun invoke(pembayaranHutangModel: PembayaranHutangModel) {
    pembayaranHutangRepository.save(pembayaranHutangModel.toEntity())
  }
}