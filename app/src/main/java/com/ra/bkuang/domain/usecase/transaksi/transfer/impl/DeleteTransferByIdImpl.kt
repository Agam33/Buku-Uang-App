package com.ra.bkuang.domain.usecase.transaksi.transfer.impl

import com.ra.bkuang.domain.mapper.toEntity
import com.ra.bkuang.domain.mapper.toModel
import com.ra.bkuang.domain.repository.AkunRepository
import com.ra.bkuang.domain.repository.TransferRepository
import com.ra.bkuang.domain.usecase.transaksi.transfer.DeleteTransferById
import com.ra.bkuang.domain.util.ResourceState
import java.util.UUID
import javax.inject.Inject

class DeleteTransferByIdImpl @Inject constructor(
  private val repository: TransferRepository,
  private val accountRepository: AkunRepository
): DeleteTransferById {

  override suspend fun invoke(uuid: UUID): ResourceState {
    return try {
      val transfer = repository.findById(uuid)

      repository.delete(transfer)

      val fromAccount = accountRepository.findById(transfer.idFromAkun).toModel()
      val toAccount = accountRepository.findById(transfer.idToAkun).toModel()

      fromAccount.total += transfer.jumlah
      toAccount.total -= transfer.jumlah

      accountRepository.update(fromAccount.toEntity())
      accountRepository.update(toAccount.toEntity())

      ResourceState.SUCCESS
    } catch (e: Exception) {
      ResourceState.FAILED
    }
  }
}