package com.ra.bkuang.features.transaction.domain.usecase.pendapatan.impl

import com.ra.bkuang.common.util.ResourceState
import com.ra.bkuang.features.account.domain.AkunRepository
import com.ra.bkuang.features.transaction.domain.PendapatanRepository
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.DeletePendapatanByIdUseCase
import java.util.UUID
import javax.inject.Inject

class DeletePendapatanByIdUseCaseImpl @Inject constructor(
  private val repository: PendapatanRepository,
  private val accountRepository: AkunRepository
): DeletePendapatanByIdUseCase {

  override suspend fun invoke(uuid: UUID): ResourceState {
      return try {
        val pendapatan = repository.findById(uuid)

        repository.delete(pendapatan)

        val account = accountRepository.findById(pendapatan.idAkun)
        account.total -= pendapatan.jumlah

        accountRepository.update(account)

        ResourceState.SUCCESS
      } catch (e: Exception) {
        ResourceState.FAILED
      }
    }
}