package com.ra.bkuang.domain.usecase.transaksi.pengeluaran.impl

import com.ra.bkuang.domain.mapper.toEntity
import com.ra.bkuang.domain.mapper.toModel
import com.ra.bkuang.domain.model.PengeluaranModel
import com.ra.bkuang.domain.repository.AkunRepository
import com.ra.bkuang.domain.repository.BudgetRepository
import com.ra.bkuang.domain.repository.PengeluaranRepository
import com.ra.bkuang.domain.usecase.transaksi.pengeluaran.SavePengeluaran
import com.ra.bkuang.util.ResourceState
import javax.inject.Inject

class SavePengeluaranImpl @Inject constructor(
  private val pengeluaranRepository: PengeluaranRepository,
  private val akunRepository: AkunRepository,
  private val budgetRepository: BudgetRepository
): SavePengeluaran {
  override suspend fun invoke(pengeluaranModel: PengeluaranModel): ResourceState {
    return try {
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

      ResourceState.SUCCESS
    } catch (e: Exception) {
     ResourceState.FAILED
    }
  }
}