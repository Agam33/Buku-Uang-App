package com.ra.budgetplan.domain.usecase.transaksi.pengeluaran.impl

import com.ra.budgetplan.domain.mapper.toModel
import com.ra.budgetplan.domain.model.PengeluaranModel
import com.ra.budgetplan.domain.repository.PengeluaranRepository
import com.ra.budgetplan.domain.usecase.transaksi.pengeluaran.GetPengeluaranById
import java.util.UUID
import javax.inject.Inject

class GetPengeluaranByIdImpl @Inject constructor(
  private val repository: PengeluaranRepository
): GetPengeluaranById {
  override suspend fun invoke(uuid: UUID): PengeluaranModel {
    return repository.findById(uuid).toModel()
  }
}