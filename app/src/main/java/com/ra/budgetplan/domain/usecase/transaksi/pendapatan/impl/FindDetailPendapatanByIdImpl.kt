package com.ra.budgetplan.domain.usecase.transaksi.pendapatan.impl

import com.ra.budgetplan.domain.entity.DetailPendapatan
import com.ra.budgetplan.domain.repository.PendapatanRepository
import com.ra.budgetplan.domain.usecase.transaksi.pendapatan.FindDetailPendapatanById
import java.util.UUID
import javax.inject.Inject

class FindDetailPendapatanByIdImpl @Inject constructor(
  private val pendapatanRepository: PendapatanRepository
): FindDetailPendapatanById {

  override suspend fun invoke(id: UUID): DetailPendapatan {
    return pendapatanRepository.findDetailById(id)
  }
}