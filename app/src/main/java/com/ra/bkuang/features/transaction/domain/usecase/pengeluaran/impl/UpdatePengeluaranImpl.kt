package com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.impl

import com.ra.bkuang.features.account.data.mapper.toEntity
import com.ra.bkuang.features.account.data.mapper.toModel
import com.ra.bkuang.features.transaction.domain.model.PengeluaranModel
import com.ra.bkuang.features.account.domain.AkunRepository
import com.ra.bkuang.features.budget.domain.BudgetRepository
import com.ra.bkuang.features.transaction.domain.PengeluaranRepository
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.UpdatePengeluaran
import com.ra.bkuang.common.util.ResourceState
import com.ra.bkuang.features.budget.data.mapper.toEntity
import com.ra.bkuang.features.budget.data.mapper.toModel
import com.ra.bkuang.features.transaction.data.mapper.toEntity
import javax.inject.Inject

class UpdatePengeluaranImpl @Inject constructor(
  private val repository: PengeluaranRepository,
  private val accountRepository: AkunRepository,
  private val budgetRepository: BudgetRepository
): UpdatePengeluaran {
  override suspend fun invoke(newPengeluaranModel: PengeluaranModel, oldPengeluaranModel: PengeluaranModel): ResourceState {
    return try {
      repository.update(newPengeluaranModel.toEntity())

      val newAccount = accountRepository.findById(newPengeluaranModel.idAkun).toModel()

      newAccount.total -= newPengeluaranModel.jumlah

      accountRepository.update(newAccount.toEntity())

      val oldAccount = accountRepository.findById(oldPengeluaranModel.idAkun).toModel()

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
        ).toModel()

        budgetModel.pengeluaran -= oldPengeluaranModel.jumlah
        budgetModel.pengeluaran += newPengeluaranModel.jumlah

        budgetRepository.update(budgetModel.toEntity())
      }

      accountRepository.update(oldAccount.toEntity())

      ResourceState.SUCCESS
    } catch (e: Exception) {
      ResourceState.FAILED
    }
 }
}