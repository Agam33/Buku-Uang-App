package com.ra.bkuang.features.debt.domain.usecase.impl

import com.ra.bkuang.features.debt.domain.PembayaranHutangRepository
import com.ra.bkuang.features.debt.domain.usecase.GetSizeListPembayaranHutangByIdUseCase
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class GetSizeListPembayaranHutangByIdUseCaseImpl @Inject constructor(
  private val pembayaranHutangRepository: PembayaranHutangRepository
): GetSizeListPembayaranHutangByIdUseCase {
  override fun invoke(id: String): Flow<Int?> {
    return pembayaranHutangRepository.getSizeListPembayaranHutangById(UUID.fromString(id))
  }
}