package com.ra.bkuang.domain.usecase.transaksi.pengeluaran.impl

import com.ra.bkuang.domain.mapper.toEntity
import com.ra.bkuang.domain.mapper.toModel
import com.ra.bkuang.domain.repository.AkunRepository
import com.ra.bkuang.domain.repository.BudgetRepository
import com.ra.bkuang.domain.repository.PengeluaranRepository
import com.ra.bkuang.domain.usecase.transaksi.pengeluaran.DeletePengeluaranById
import com.ra.bkuang.util.ResourceState
import java.util.UUID
import javax.inject.Inject

class DeletePengeluaranByIdImpl @Inject constructor(
  private val repository: PengeluaranRepository,
  private val accountRepository: AkunRepository,
  private val budgetRepository: BudgetRepository
): DeletePengeluaranById {
  override suspend fun invoke(uuid: UUID): ResourceState {
    return try {
      val pengeluaran = repository.findById(uuid)

      repository.delete(pengeluaran)

      val account = accountRepository.findById(pengeluaran.idAkun).toModel()
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
        ).toModel()

        budgetModel.pengeluaran -= pengeluaran.jumlah

        budgetRepository.update(budgetModel.toEntity())
      }

      accountRepository.update(account.toEntity())

      ResourceState.SUCCESS
    } catch (e: Exception) {
      ResourceState.FAILED
    }
  }
}