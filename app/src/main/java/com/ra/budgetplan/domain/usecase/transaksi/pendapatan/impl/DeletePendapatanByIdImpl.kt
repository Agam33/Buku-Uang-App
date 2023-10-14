package com.ra.budgetplan.domain.usecase.transaksi.pendapatan.impl

import com.ra.budgetplan.domain.mapper.toEntity
import com.ra.budgetplan.domain.mapper.toModel
import com.ra.budgetplan.domain.repository.AkunRepository
import com.ra.budgetplan.domain.repository.PendapatanRepository
import com.ra.budgetplan.domain.usecase.transaksi.pendapatan.DeletePendapatanById
import com.ra.budgetplan.util.ResourceState
import java.util.UUID
import javax.inject.Inject

class DeletePendapatanByIdImpl @Inject constructor(
  private val repository: PendapatanRepository,
  private val accountRepository: AkunRepository
): DeletePendapatanById {
  override suspend fun invoke(uuid: UUID): ResourceState {
    return try {
      val pendapatan = repository.findById(uuid)

      repository.delete(pendapatan)

      val account = accountRepository.findById(pendapatan.idTabungan).toModel()
      account.total -= pendapatan.jumlah

      accountRepository.update(account.toEntity())

      ResourceState.SUCCESS
    } catch (e: Exception) {
      ResourceState.FAILED
    }
  }
}