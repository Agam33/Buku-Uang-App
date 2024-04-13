package com.ra.bkuang.features.transaction.domain.usecase.pendapatan.impl

import com.ra.bkuang.features.account.data.mapper.toEntity
import com.ra.bkuang.features.account.data.mapper.toModel
import com.ra.bkuang.features.account.domain.AkunRepository
import com.ra.bkuang.features.transaction.domain.PendapatanRepository
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.DeletePendapatanById
import com.ra.bkuang.common.util.ResourceState
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

      val account = accountRepository.findById(pendapatan.idTabungan)
      account.total -= pendapatan.jumlah

      accountRepository.update(account)

      ResourceState.SUCCESS
    } catch (e: Exception) {
      ResourceState.FAILED
    }
  }
}