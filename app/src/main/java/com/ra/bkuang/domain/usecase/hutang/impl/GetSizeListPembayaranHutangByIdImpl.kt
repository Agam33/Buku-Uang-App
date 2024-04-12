package com.ra.bkuang.domain.usecase.hutang.impl

import com.ra.bkuang.domain.repository.PembayaranHutangRepository
import com.ra.bkuang.domain.usecase.hutang.GetSizeListPembayaranHutangById
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class GetSizeListPembayaranHutangByIdImpl @Inject constructor(
  private val pembayaranHutangRepository: PembayaranHutangRepository
): GetSizeListPembayaranHutangById {
  override fun invoke(id: String): Flow<Int?> {
    return pembayaranHutangRepository.getSizeListPembayaranHutangById(UUID.fromString(id))
  }
}