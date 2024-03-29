package com.ra.bkuang.domain.usecase.transaksi.pendapatan.impl

import com.ra.bkuang.data.local.database.entity.DetailPendapatan
import com.ra.bkuang.domain.repository.PendapatanRepository
import com.ra.bkuang.domain.usecase.transaksi.pendapatan.FindDetailPendapatanById
import java.util.UUID
import javax.inject.Inject

class FindDetailPendapatanByIdImpl @Inject constructor(
  private val pendapatanRepository: PendapatanRepository
): FindDetailPendapatanById {

  override suspend fun invoke(id: UUID): DetailPendapatan {
    return pendapatanRepository.findDetailById(id)
  }
}