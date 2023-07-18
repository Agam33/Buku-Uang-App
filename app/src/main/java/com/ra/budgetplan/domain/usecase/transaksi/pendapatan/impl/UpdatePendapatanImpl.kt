package com.ra.budgetplan.domain.usecase.transaksi.pendapatan.impl

import com.ra.budgetplan.domain.mapper.toEntity
import com.ra.budgetplan.domain.mapper.toModel
import com.ra.budgetplan.domain.model.PendapatanModel
import com.ra.budgetplan.domain.repository.AkunRepository
import com.ra.budgetplan.domain.repository.PendapatanRepository
import com.ra.budgetplan.domain.usecase.transaksi.pendapatan.UpdatePendapatan
import javax.inject.Inject

class UpdatePendapatanImpl @Inject constructor(
  private val repository: PendapatanRepository,
  private val accountRepository: AkunRepository
): UpdatePendapatan {
  override suspend fun invoke(newPendapatanModel: PendapatanModel, oldPendapatanModel: PendapatanModel) {
    repository.update(newPendapatanModel.toEntity())

    val newAccount = accountRepository.findById(newPendapatanModel.idAkun).toModel()

    newAccount.total += newPendapatanModel.jumlah

    accountRepository.update(newAccount.toEntity())

    val oldAccount = accountRepository.findById(oldPendapatanModel.idAkun).toModel()

    oldAccount.total -= oldPendapatanModel.jumlah

    accountRepository.update(oldAccount.toEntity())

  }
}