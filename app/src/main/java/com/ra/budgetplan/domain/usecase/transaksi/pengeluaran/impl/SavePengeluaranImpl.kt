package com.ra.budgetplan.domain.usecase.transaksi.pengeluaran.impl

import com.ra.budgetplan.domain.mapper.toEntity
import com.ra.budgetplan.domain.mapper.toModel
import com.ra.budgetplan.domain.model.PengeluaranModel
import com.ra.budgetplan.domain.repository.AkunRepository
import com.ra.budgetplan.domain.repository.BudgetRepository
import com.ra.budgetplan.domain.repository.PengeluaranRepository
import com.ra.budgetplan.domain.usecase.transaksi.pengeluaran.SavePengeluaran
import javax.inject.Inject

class SavePengeluaranImpl @Inject constructor(
  private val pengeluaranRepository: PengeluaranRepository,
  private val akunRepository: AkunRepository,
  private val budgetRepository: BudgetRepository
): SavePengeluaran {
  override suspend fun invoke(pengeluaranModel: PengeluaranModel) {
    val account = akunRepository.findById(pengeluaranModel.idAkun).toModel()

    pengeluaranRepository.save(pengeluaranModel.toEntity())

    account.total -= pengeluaranModel.jumlah

    val fromDate = pengeluaranModel.updatedAt.toLocalDate().withDayOfMonth(1)
    val toDate = pengeluaranModel.updatedAt.toLocalDate().withDayOfMonth(pengeluaranModel.updatedAt.toLocalDate().dayOfMonth)
    val katId = pengeluaranModel.idKategori

    val isBudgetExist = budgetRepository.isExistByDateAndKategoriId(
      fromDate,
      toDate,
      katId
    )

    if(isBudgetExist) {
      val budgetModel = budgetRepository.findBudgetByDateAndKategoriId(
        fromDate,
        toDate,
        katId
      ).toModel()

      budgetModel.pengeluaran += pengeluaranModel.jumlah

      budgetRepository.update(budgetModel.toEntity())
    }

    akunRepository.update(account.toEntity())
  }
}