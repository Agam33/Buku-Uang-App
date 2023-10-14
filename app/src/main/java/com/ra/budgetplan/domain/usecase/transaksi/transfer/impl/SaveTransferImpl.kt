package com.ra.budgetplan.domain.usecase.transaksi.transfer.impl

import com.ra.budgetplan.domain.mapper.toEntity
import com.ra.budgetplan.domain.mapper.toModel
import com.ra.budgetplan.domain.model.TransferModel
import com.ra.budgetplan.domain.repository.AkunRepository
import com.ra.budgetplan.domain.repository.TransferRepository
import com.ra.budgetplan.domain.usecase.transaksi.transfer.SaveTransfer
import com.ra.budgetplan.util.ResourceState
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SaveTransferImpl @Inject constructor(
  private val transferRepository: TransferRepository,
  private val akunRepository: AkunRepository
): SaveTransfer {

  override suspend fun invoke(transferModel: TransferModel): ResourceState {
    return try {
      val toAccount = akunRepository.findById(transferModel.idToAkun).toModel()
      val fromAccount = akunRepository.findById(transferModel.idFromAkun).toModel()

      toAccount.total += transferModel.jumlah
      fromAccount.total -= transferModel.jumlah

      transferRepository.save(transferModel.toEntity())

      akunRepository.update(toAccount.toEntity())
      akunRepository.update(fromAccount.toEntity())

      ResourceState.SUCCESS
    } catch (e: Exception) {
      ResourceState.FAILED
    }
  }
}