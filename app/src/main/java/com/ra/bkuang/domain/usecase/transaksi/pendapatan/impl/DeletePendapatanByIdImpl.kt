package com.ra.bkuang.domain.usecase.transaksi.pendapatan.impl

import com.ra.bkuang.domain.mapper.toEntity
import com.ra.bkuang.domain.mapper.toModel
import com.ra.bkuang.domain.repository.AkunRepository
import com.ra.bkuang.domain.repository.PendapatanRepository
import com.ra.bkuang.domain.usecase.transaksi.pendapatan.DeletePendapatanById
import com.ra.bkuang.util.ResourceState
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