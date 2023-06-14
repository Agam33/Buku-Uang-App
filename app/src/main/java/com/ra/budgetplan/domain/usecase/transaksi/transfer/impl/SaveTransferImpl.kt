package com.ra.budgetplan.domain.usecase.transaksi.transfer.impl

import com.ra.budgetplan.domain.mapper.toEntity
import com.ra.budgetplan.domain.mapper.toModel
import com.ra.budgetplan.domain.model.TransferModel
import com.ra.budgetplan.domain.repository.AkunRepository
import com.ra.budgetplan.domain.repository.TransferRepository
import com.ra.budgetplan.domain.usecase.transaksi.transfer.SaveTransfer
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SaveTransferImpl @Inject constructor(
  private val transferRepository: TransferRepository,
  private val akunRepository: AkunRepository
): SaveTransfer {

  override suspend fun invoke(transferModel: TransferModel) {
    val toAccount = akunRepository.findById(transferModel.idToAkun).first().toModel()
    val fromAccount = akunRepository.findById(transferModel.idFromAkun).first().toModel()

    toAccount.total -= transferModel.jumlah
    fromAccount.total += transferModel.jumlah

    akunRepository.update(toAccount.toEntity())
    akunRepository.update(fromAccount.toEntity())

    transferRepository.save(transferModel.toEntity())
  }
}