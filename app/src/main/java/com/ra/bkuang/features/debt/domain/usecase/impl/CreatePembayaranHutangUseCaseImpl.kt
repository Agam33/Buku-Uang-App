package com.ra.bkuang.features.debt.domain.usecase.impl

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.debt.domain.PembayaranHutangRepository
import com.ra.bkuang.features.debt.domain.model.PembayaranHutangModel
import com.ra.bkuang.features.debt.domain.usecase.CreatePembayaranHutangUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreatePembayaranHutangUseCaseImpl @Inject constructor(
  private val pembayaranHutangRepository: PembayaranHutangRepository
): CreatePembayaranHutangUseCase {
  override fun invoke(pembayaranHutangModel: PembayaranHutangModel): Flow<Result<Boolean>> {
    return pembayaranHutangRepository.save(pembayaranHutangModel)
  }
}