package com.ra.budgetplan.domain.usecase.hutang.impl

import com.ra.budgetplan.domain.repository.PembayaranHutangRepository
import com.ra.budgetplan.domain.usecase.hutang.GetSizeListPembayaranHutangById
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class GetSizeListPembayaranHutangByIdImpl @Inject constructor(
  private val pembayaranHutangRepository: PembayaranHutangRepository
): GetSizeListPembayaranHutangById {
  override fun invoke(id: UUID): Flow<Int?> {
    return pembayaranHutangRepository.getSizeListPembayaranHutangById(id)
  }
}