package com.ra.budgetplan.domain.usecase.transaksi.pengeluaran.impl

import com.ra.budgetplan.domain.mapper.toEntity
import com.ra.budgetplan.domain.mapper.toModel
import com.ra.budgetplan.domain.model.PengeluaranModel
import com.ra.budgetplan.domain.repository.AkunRepository
import com.ra.budgetplan.domain.repository.BudgetRepository
import com.ra.budgetplan.domain.repository.PengeluaranRepository
import com.ra.budgetplan.domain.usecase.transaksi.pengeluaran.UpdatePengeluaran
import javax.inject.Inject

class UpdatePengeluaranImpl @Inject constructor(
  private val repository: PengeluaranRepository,
  private val accountRepository: AkunRepository,
  private val budgetRepository: BudgetRepository
): UpdatePengeluaran {
  override suspend fun invoke(newPengeluaranModel: PengeluaranModel, oldPengeluaranModel: PengeluaranModel) {
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
  }
}