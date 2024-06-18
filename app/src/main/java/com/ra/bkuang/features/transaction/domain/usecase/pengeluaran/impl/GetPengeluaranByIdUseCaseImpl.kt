package com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.impl

import com.ra.bkuang.features.transaction.domain.repository.PengeluaranRepository
import com.ra.bkuang.features.transaction.domain.model.PengeluaranModel
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.GetPengeluaranByIdUseCase
import java.util.UUID
import javax.inject.Inject

class GetPengeluaranByIdUseCaseImpl @Inject constructor(
  private val repository: PengeluaranRepository
): GetPengeluaranByIdUseCase {
  override suspend fun invoke(uuid: UUID): PengeluaranModel {
    return repository.findById(uuid)
  }
}