package com.ra.budgetplan.domain.usecase.transaksi.transfer.impl

import com.ra.budgetplan.domain.mapper.toEntity
import com.ra.budgetplan.domain.mapper.toModel
import com.ra.budgetplan.domain.repository.AkunRepository
import com.ra.budgetplan.domain.repository.TransferRepository
import com.ra.budgetplan.domain.usecase.transaksi.transfer.DeleteTransferById
import java.util.UUID
import javax.inject.Inject

class DeleteTransferByIdImpl @Inject constructor(
  private val repository: TransferRepository,
  private val accountRepository: AkunRepository
): DeleteTransferById {

  override suspend fun invoke(uuid: UUID) {
    val transfer = repository.findById(uuid)

    repository.delete(transfer)

    val fromAccount = accountRepository.findById(transfer.idFromAkun).toModel()
    val toAccount = accountRepository.findById(transfer.idToAkun).toModel()

    fromAccount.total += transfer.jumlah
    toAccount.total -= transfer.jumlah

    accountRepository.update(fromAccount.toEntity())
    accountRepository.update(toAccount.toEntity())
  }
}