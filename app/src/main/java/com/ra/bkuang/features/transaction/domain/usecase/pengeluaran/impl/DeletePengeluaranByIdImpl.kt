package com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.impl

import com.ra.bkuang.features.account.domain.AkunRepository
import com.ra.bkuang.features.budget.domain.BudgetRepository
import com.ra.bkuang.features.transaction.domain.PengeluaranRepository
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.DeletePengeluaranById
import com.ra.bkuang.common.util.ResourceState
import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.account.data.mapper.toEntity
import com.ra.bkuang.features.account.data.mapper.toModel
import com.ra.bkuang.features.budget.data.mapper.toEntity
import com.ra.bkuang.features.budget.data.mapper.toModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class DeletePengeluaranByIdImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispather: CoroutineDispatcher,
  private val repository: PengeluaranRepository,
  private val accountRepository: AkunRepository,
  private val budgetRepository: BudgetRepository
): DeletePengeluaranById {
  override suspend fun invoke(uuid: UUID): ResourceState = withContext(ioDispather) {
    return@withContext try {
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