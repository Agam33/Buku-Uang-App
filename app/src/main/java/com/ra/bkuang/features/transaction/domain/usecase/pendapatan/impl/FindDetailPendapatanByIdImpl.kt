package com.ra.bkuang.features.transaction.domain.usecase.pendapatan.impl

import com.ra.bkuang.features.transaction.data.entity.DetailPendapatan
import com.ra.bkuang.features.transaction.domain.PendapatanRepository
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.FindDetailPendapatanById
import java.util.UUID
import javax.inject.Inject

class FindDetailPendapatanByIdImpl @Inject constructor(
  private val pendapatanRepository: PendapatanRepository
): FindDetailPendapatanById {

  override suspend fun invoke(id: UUID): DetailPendapatan {
    return pendapatanRepository.findDetailById(id)
  }
}