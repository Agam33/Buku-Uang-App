package com.ra.bkuang.features.transaction.domain.usecase.pendapatan.impl

import com.ra.bkuang.features.transaction.domain.PendapatanRepository
import com.ra.bkuang.features.transaction.domain.model.PendapatanModel
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.GetPendapatanByIdUseCase
import java.util.UUID
import javax.inject.Inject

class GetPendapatanByIdUseCaseImpl @Inject constructor(
  private val repository: PendapatanRepository
): GetPendapatanByIdUseCase {
  override suspend fun invoke(uuid: UUID): PendapatanModel {
    return repository.findById(uuid)
  }
}