package com.ra.bkuang.features.transaction.domain.usecase.transfer.impl

import com.ra.bkuang.features.transaction.data.entity.DetailTransfer
import com.ra.bkuang.features.transaction.domain.TransferRepository
import com.ra.bkuang.features.transaction.domain.usecase.transfer.FindDetailTransferByIdUseCase
import java.util.UUID
import javax.inject.Inject

class FindDetailTransferByIdUseCaseImpl @Inject constructor(
  private val transferRepository: TransferRepository
): FindDetailTransferByIdUseCase {

  override suspend fun invoke(id: UUID): DetailTransfer {
    return transferRepository.findDetailById(id)
  }
}