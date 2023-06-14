package com.ra.budgetplan.domain.usecase.transaksi.pengeluaran.impl

import com.ra.budgetplan.domain.mapper.toEntity
import com.ra.budgetplan.domain.mapper.toModel
import com.ra.budgetplan.domain.model.PengeluaranModel
import com.ra.budgetplan.domain.repository.AkunRepository
import com.ra.budgetplan.domain.repository.PengeluaranRepository
import com.ra.budgetplan.domain.usecase.transaksi.pengeluaran.SavePengeluaran
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SavePengeluaranImpl @Inject constructor(
  private val pengeluaranRepository: PengeluaranRepository,
  private val akunRepository: AkunRepository
): SavePengeluaran {
  override suspend fun invoke(pengeluaranModel: PengeluaranModel) {
    val account = akunRepository.findById(pengeluaranModel.idAkun).first().toModel()

    account.total -= pengeluaranModel.jumlah

    akunRepository.update(account.toEntity())
    pengeluaranRepository.save(pengeluaranModel.toEntity())
  }
}