package com.ra.bkuang.features.transaction.domain.usecase.transfer.impl

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.transaction.domain.repository.TransferRepository
import com.ra.bkuang.features.transaction.domain.model.TransferModel
import com.ra.bkuang.features.transaction.domain.usecase.transfer.SaveTransferUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveTransferUseCaseImpl @Inject constructor(
  private val transferRepository: TransferRepository,
): SaveTransferUseCase {

  override fun invoke(transferModel: TransferModel): Flow<Result<Boolean>> {
    return transferRepository.save(transferModel)
  }
}