package com.ra.budgetplan.domain.usecase.transaksi.pendapatan.impl

import com.ra.budgetplan.domain.mapper.toModel
import com.ra.budgetplan.domain.model.PendapatanModel
import com.ra.budgetplan.domain.repository.PendapatanRepository
import com.ra.budgetplan.domain.usecase.transaksi.pendapatan.GetPendapatanById
import java.util.UUID
import javax.inject.Inject

class GetPendapatanByIdImpl @Inject constructor(
  private val repository: PendapatanRepository
): GetPendapatanById {
  override suspend fun invoke(uuid: UUID): PendapatanModel {
    return repository.findById(uuid).toModel()
  }
}