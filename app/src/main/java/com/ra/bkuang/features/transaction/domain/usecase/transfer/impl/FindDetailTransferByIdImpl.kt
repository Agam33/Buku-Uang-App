package com.ra.bkuang.features.transaction.domain.usecase.transfer.impl

import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.transaction.data.entity.DetailTransfer
import com.ra.bkuang.features.transaction.domain.TransferRepository
import com.ra.bkuang.features.transaction.domain.usecase.transfer.FindDetailTransferById
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class FindDetailTransferByIdImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispather: CoroutineDispatcher,
  private val transferRepository: TransferRepository
): FindDetailTransferById {

  override suspend fun invoke(id: UUID): DetailTransfer = withContext(ioDispather) {
    return@withContext transferRepository.findDetailById(id)
  }
}