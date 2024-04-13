package com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.impl

import com.ra.bkuang.features.account.data.mapper.toEntity
import com.ra.bkuang.features.account.data.mapper.toModel
import com.ra.bkuang.features.transaction.domain.model.PengeluaranModel
import com.ra.bkuang.features.account.domain.AkunRepository
import com.ra.bkuang.features.budget.domain.BudgetRepository
import com.ra.bkuang.features.transaction.domain.PengeluaranRepository
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.SavePengeluaran
import com.ra.bkuang.common.util.ResourceState
import com.ra.bkuang.features.budget.data.mapper.toEntity
import com.ra.bkuang.features.budget.data.mapper.toModel
import com.ra.bkuang.features.transaction.data.mapper.toEntity
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