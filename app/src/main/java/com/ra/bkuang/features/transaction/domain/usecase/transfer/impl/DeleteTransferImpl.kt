package com.ra.bkuang.features.transaction.domain.usecase.transfer.impl

import com.ra.bkuang.common.util.ResourceState
import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.transaction.domain.TransferRepository
import com.ra.bkuang.features.transaction.domain.model.TransferModel
import com.ra.bkuang.features.transaction.domain.usecase.transfer.DeleteTransfer
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteTransferImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispather: CoroutineDispatcher,
  private val repository: TransferRepository
): DeleteTransfer {
  override suspend fun invoke(transferModel: TransferModel): ResourceState = withContext(ioDispather) {
    return@withContext try {
      repository.delete(transferModel)
      ResourceState.SUCCESS
    } catch (e: Exception) {
      ResourceState.FAILED
    }
  }
}