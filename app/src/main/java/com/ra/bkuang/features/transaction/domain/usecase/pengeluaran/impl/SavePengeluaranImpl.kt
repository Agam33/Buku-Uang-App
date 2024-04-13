package com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.impl

import com.ra.bkuang.common.util.ResourceState
import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.account.domain.AkunRepository
import com.ra.bkuang.features.budget.domain.BudgetRepository
import com.ra.bkuang.features.transaction.domain.PengeluaranRepository
import com.ra.bkuang.features.transaction.domain.model.PengeluaranModel
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.SavePengeluaran
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SavePengeluaranImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispather: CoroutineDispatcher,
  private val pengeluaranRepository: PengeluaranRepository,
  private val akunRepository: AkunRepository,
  private val budgetRepository: BudgetRepository
): SavePengeluaran {
  override suspend fun invoke(pengeluaranModel: PengeluaranModel): ResourceState = withContext(ioDispather) {
    return@withContext try {
      val account = akunRepository.findById(pengeluaranModel.idAkun)

      pengeluaranRepository.save(pengeluaranModel)

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
        )

        budgetModel.pengeluaran += pengeluaranModel.jumlah

        budgetRepository.update(budgetModel)
      }

      akunRepository.update(account)

      ResourceState.SUCCESS
    } catch (e: Exception) {
     ResourceState.FAILED
    }
  }
}