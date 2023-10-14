package com.ra.bkuang.domain.usecase.transaksi.pengeluaran.impl

import com.ra.bkuang.domain.mapper.toModel
import com.ra.bkuang.domain.model.PengeluaranModel
import com.ra.bkuang.domain.repository.PengeluaranRepository
import com.ra.bkuang.domain.usecase.transaksi.pengeluaran.GetPengeluaranById
import java.util.UUID
import javax.inject.Inject

class GetPengeluaranByIdImpl @Inject constructor(
  private val repository: PengeluaranRepository
): GetPengeluaranById {
  override suspend fun invoke(uuid: UUID): PengeluaranModel {
    return repository.findById(uuid).toModel()
  }
}