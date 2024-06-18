package com.ra.bkuang.features.transaction.domain.usecase.pendapatan.impl

import com.ra.bkuang.features.transaction.domain.model.PendapatanModel
import com.ra.bkuang.features.transaction.domain.repository.PendapatanRepository
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.GetPendapatanByIdUseCase
import java.util.UUID
import javax.inject.Inject

class GetPendapatanByIdUseCaseImpl @Inject constructor(
  private val incomeRepository: PendapatanRepository
): GetPendapatanByIdUseCase {
  override suspend fun invoke(id: String): PendapatanModel {
    return incomeRepository.findById(UUID.fromString(id))
  }
}