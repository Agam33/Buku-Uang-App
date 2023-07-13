package com.ra.budgetplan.domain.usecase.transaksi.pengeluaran.impl

import com.ra.budgetplan.domain.mapper.toEntity
import com.ra.budgetplan.domain.mapper.toModel
import com.ra.budgetplan.domain.repository.AkunRepository
import com.ra.budgetplan.domain.repository.BudgetRepository
import com.ra.budgetplan.domain.repository.PengeluaranRepository
import com.ra.budgetplan.domain.usecase.transaksi.pengeluaran.DeletePengeluaranById
import java.util.UUID
import javax.inject.Inject

class DeletePengeluaranByIdImpl @Inject constructor(
  private val repository: PengeluaranRepository,
  private val accountRepository: AkunRepository,
  private val budgetRepository: BudgetRepository
): DeletePengeluaranById {
  override suspend fun invoke(uuid: UUID) {
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
  }
}