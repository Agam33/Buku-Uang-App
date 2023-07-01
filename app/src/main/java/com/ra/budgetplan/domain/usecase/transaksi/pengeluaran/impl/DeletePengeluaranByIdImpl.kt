package com.ra.budgetplan.domain.usecase.transaksi.pengeluaran.impl

import com.ra.budgetplan.domain.mapper.toEntity
import com.ra.budgetplan.domain.mapper.toModel
import com.ra.budgetplan.domain.repository.AkunRepository
import com.ra.budgetplan.domain.repository.PengeluaranRepository
import com.ra.budgetplan.domain.usecase.transaksi.pengeluaran.DeletePengeluaranById
import java.util.UUID
import javax.inject.Inject

class DeletePengeluaranByIdImpl @Inject constructor(
  private val repository: PengeluaranRepository,
  private val accountRepository: AkunRepository
): DeletePengeluaranById {
  override suspend fun invoke(uuid: UUID) {
    val pengeluaran = repository.findById(uuid)

    repository.delete(pengeluaran)

    val account = accountRepository.findById(pengeluaran.idAkun).toModel()
    account.total += pengeluaran.jumlah

    accountRepository.update(account.toEntity())
  }
}