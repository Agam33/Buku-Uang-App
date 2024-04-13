package com.ra.bkuang.features.transaction.domain.usecase.pendapatan.impl

import com.ra.bkuang.common.util.ResourceState
import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.account.domain.AkunRepository
import com.ra.bkuang.features.transaction.domain.PendapatanRepository
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.DeletePendapatanById
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class DeletePendapatanByIdImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispather: CoroutineDispatcher,
  private val repository: PendapatanRepository,
  private val accountRepository: AkunRepository
): DeletePendapatanById {
  override suspend fun invoke(uuid: UUID): ResourceState {
    return withContext(ioDispather) {
      return@withContext  try {
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
}