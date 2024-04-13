package com.ra.bkuang.features.transaction.domain.usecase.pendapatan.impl

import com.ra.bkuang.features.transaction.data.mapper.toModel
import com.ra.bkuang.features.transaction.domain.PendapatanRepository
import com.ra.bkuang.features.transaction.domain.model.PendapatanModel
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.GetPendapatanById
import java.util.UUID
import javax.inject.Inject

class GetPendapatanByIdImpl @Inject constructor(
  private val repository: PendapatanRepository
): GetPendapatanById {
  override suspend fun invoke(uuid: UUID): PendapatanModel {
    return repository.findById(uuid).toModel()
  }
}