package com.ra.budgetplan.domain.usecase.transaksi.pengeluaran.impl

import com.ra.budgetplan.domain.entity.DetailPengeluaran
import com.ra.budgetplan.domain.repository.PengeluaranRepository
import com.ra.budgetplan.domain.usecase.transaksi.pengeluaran.FindDetailPengeluaranById
import java.util.UUID
import javax.inject.Inject

class FindDetailPengeluaranByIdImpl @Inject constructor(
  private val pengeluaranRepository: PengeluaranRepository
): FindDetailPengeluaranById {

  override suspend fun invoke(id: UUID): DetailPengeluaran {
    return pengeluaranRepository.findDetailById(id)
  }
}