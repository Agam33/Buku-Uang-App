package com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.impl

import com.ra.bkuang.features.transaction.data.mapper.toModel
import com.ra.bkuang.features.transaction.domain.PengeluaranRepository
import com.ra.bkuang.features.transaction.domain.model.PengeluaranModel
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.GetPengeluaranById
import java.util.UUID
import javax.inject.Inject

class GetPengeluaranByIdImpl @Inject constructor(
  private val repository: PengeluaranRepository
): GetPengeluaranById {
  override suspend fun invoke(uuid: UUID): PengeluaranModel {
    return repository.findById(uuid).toModel()
  }
}