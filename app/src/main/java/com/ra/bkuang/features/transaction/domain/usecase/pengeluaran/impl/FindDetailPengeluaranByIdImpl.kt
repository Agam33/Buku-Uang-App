package com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.impl

import com.ra.bkuang.features.transaction.data.entity.DetailPengeluaran
import com.ra.bkuang.features.transaction.domain.PengeluaranRepository
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.FindDetailPengeluaranById
import java.util.UUID
import javax.inject.Inject

class FindDetailPengeluaranByIdImpl @Inject constructor(
  private val pengeluaranRepository: PengeluaranRepository
): FindDetailPengeluaranById {

  override suspend fun invoke(id: UUID): DetailPengeluaran {
    return pengeluaranRepository.findDetailById(id)
  }
}