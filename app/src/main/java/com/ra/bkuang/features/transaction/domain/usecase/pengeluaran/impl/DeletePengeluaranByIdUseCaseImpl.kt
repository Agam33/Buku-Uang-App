package com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.impl

import com.ra.bkuang.common.util.ResourceState
import com.ra.bkuang.features.account.domain.AkunRepository
import com.ra.bkuang.features.budget.domain.BudgetRepository
import com.ra.bkuang.features.transaction.domain.PengeluaranRepository
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.DeletePengeluaranByIdUseCase
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class DeletePengeluaranByIdUseCaseImpl @Inject constructor(
  private val repository: PengeluaranRepository,
  private val accountRepository: AkunRepository,
  private val budgetRepository: BudgetRepository
): DeletePengeluaranByIdUseCase {
  override suspend fun invoke(uuid: UUID): ResourceState {
    return try {
      val pengeluaran = repository.findById(uuid)

      repository.delete(pengeluaran)

      val account = accountRepository.findById(pengeluaran.idAkun)
      account.total += pengeluaran.jumlah

      val fromDate = pengeluaran.updatedAt.toLocalDate().withDayOfMonth(1)
      val toDate = pengeluaran.updatedAt.toLocalDate().withDayOfMonth(pengeluaran.updatedAt.toLocalDate().dayOfMonth)
      val katId = pengeluaran.idKategori

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

        budgetModel.pengeluaran -= pengeluaran.jumlah

        budgetRepository.update(budgetModel)
      }

      accountRepository.update(account)

      ResourceState.SUCCESS
    } catch (e: Exception) {
      ResourceState.FAILED
    }
  }
}