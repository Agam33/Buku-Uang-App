package com.ra.bkuang.features.transaction.domain.usecase.transfer.impl

import com.ra.bkuang.common.util.ResourceState
import com.ra.bkuang.features.account.domain.AkunRepository
import com.ra.bkuang.features.transaction.domain.TransferRepository
import com.ra.bkuang.features.transaction.domain.usecase.transfer.DeleteTransferByIdUseCase
import java.util.UUID
import javax.inject.Inject

class DeleteTransferByIdUseCaseImpl @Inject constructor(
  private val repository: TransferRepository,
  private val accountRepository: AkunRepository
): DeleteTransferByIdUseCase {

  override suspend fun invoke(uuid: UUID): ResourceState {
    return try {
      val transfer = repository.findById(uuid)

      repository.delete(transfer)

      val fromAccount = accountRepository.findById(transfer.idFromAkun)
      val toAccount = accountRepository.findById(transfer.idToAkun)

      fromAccount.total += transfer.jumlah
      toAccount.total -= transfer.jumlah

      accountRepository.update(fromAccount)
      accountRepository.update(toAccount)

      ResourceState.SUCCESS
    } catch (e: Exception) {
      ResourceState.FAILED
    }
  }
}