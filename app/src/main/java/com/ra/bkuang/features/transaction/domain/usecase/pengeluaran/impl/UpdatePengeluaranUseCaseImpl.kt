package com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.impl

import com.ra.bkuang.common.util.ResourceState
import com.ra.bkuang.features.account.domain.AkunRepository
import com.ra.bkuang.features.budget.domain.BudgetRepository
import com.ra.bkuang.features.transaction.domain.PengeluaranRepository
import com.ra.bkuang.features.transaction.domain.model.PengeluaranModel
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.UpdatePengeluaranUseCase
import javax.inject.Inject

class UpdatePengeluaranUseCaseImpl @Inject constructor(
  private val repository: PengeluaranRepository,
  private val accountRepository: AkunRepository,
  private val budgetRepository: BudgetRepository
): UpdatePengeluaranUseCase {
  override suspend fun invoke(
    newPengeluaranModel: PengeluaranModel,
    oldPengeluaranModel: PengeluaranModel
  ): ResourceState {
    return try {
      repository.update(newPengeluaranModel)

      val newAccount = accountRepository.findById(newPengeluaranModel.idAkun)

      newAccount.total -= newPengeluaranModel.jumlah

      accountRepository.update(newAccount)

      val oldAccount = accountRepository.findById(oldPengeluaranModel.idAkun)

      oldAccount.total += oldPengeluaranModel.jumlah

      val fromDate = oldPengeluaranModel.updatedAt.toLocalDate().withDayOfMonth(1)
      val toDate = oldPengeluaranModel.updatedAt.toLocalDate().withDayOfMonth(oldPengeluaranModel.updatedAt.toLocalDate().dayOfMonth)
      val katId = oldPengeluaranModel.idKategori

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

        budgetModel.pengeluaran -= oldPengeluaranModel.jumlah
        budgetModel.pengeluaran += newPengeluaranModel.jumlah

        budgetRepository.update(budgetModel)
      }

      accountRepository.update(oldAccount)

      ResourceState.SUCCESS
    } catch (e: Exception) {
      ResourceState.FAILED
    }
 }
}