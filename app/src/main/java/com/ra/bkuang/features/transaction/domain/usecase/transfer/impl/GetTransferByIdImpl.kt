package com.ra.bkuang.features.transaction.domain.usecase.transfer.impl

import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.transaction.data.mapper.toModel
import com.ra.bkuang.features.transaction.domain.TransferRepository
import com.ra.bkuang.features.transaction.domain.model.TransferModel
import com.ra.bkuang.features.transaction.domain.usecase.transfer.GetTransferById
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class GetTransferByIdImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispather: CoroutineDispatcher,
  private val repository: TransferRepository
): GetTransferById {
  override suspend fun invoke(uuid: UUID): TransferModel = withContext(ioDispather) {
    return@withContext repository.findById(uuid)
  }
}