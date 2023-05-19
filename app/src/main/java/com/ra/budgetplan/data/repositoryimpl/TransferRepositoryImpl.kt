package com.ra.budgetplan.data.repositoryimpl

import com.ra.budgetplan.data.local.TransferLocalDataSource
import com.ra.budgetplan.domain.mapper.TransferMapper
import com.ra.budgetplan.domain.model.TransferModel
import com.ra.budgetplan.domain.repository.TransferRepository
import javax.inject.Inject

class TransferRepositoryImpl @Inject constructor(
  private val localDataSource: TransferLocalDataSource
): TransferRepository {
  override suspend fun save(transferModel: TransferModel) {
    return localDataSource.saveTransfer(TransferMapper.transferToEntity(transferModel))
  }

  override suspend fun delete(transferModel: TransferModel) {
    return localDataSource.deleteTransfer(TransferMapper.transferToEntity(transferModel))
  }

  override suspend fun update(transferModel: TransferModel) {
    return localDataSource.updateTransfer(TransferMapper.transferToEntity(transferModel))
  }
}