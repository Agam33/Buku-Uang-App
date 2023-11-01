package com.ra.bkuang.domain.usecase.transaksi.pengeluaran.impl

import com.ra.bkuang.data.entity.DetailPengeluaran
import com.ra.bkuang.domain.repository.PengeluaranRepository
import com.ra.bkuang.domain.usecase.transaksi.pengeluaran.FindDetailPengeluaranById
import java.util.UUID
import javax.inject.Inject

class FindDetailPengeluaranByIdImpl @Inject constructor(
  private val pengeluaranRepository: PengeluaranRepository
): FindDetailPengeluaranById {

  override suspend fun invoke(id: UUID): DetailPengeluaran {
    return pengeluaranRepository.findDetailById(id)
  }
}