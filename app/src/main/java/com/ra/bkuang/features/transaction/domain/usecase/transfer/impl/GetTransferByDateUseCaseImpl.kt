package com.ra.bkuang.features.transaction.domain.usecase.transfer.impl

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.transaction.data.entity.DetailTransfer
import com.ra.bkuang.features.transaction.domain.repository.TransferRepository
import com.ra.bkuang.features.transaction.domain.usecase.transfer.GetTransferByDateUseCase
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import javax.inject.Inject

class GetTransferByDateUseCaseImpl @Inject constructor(
  private val repository: TransferRepository
): GetTransferByDateUseCase {
  override fun invoke(
    fromDate: LocalDateTime,
    toDate: LocalDateTime
  ): Flow<Result<List<DetailTransfer>>> {
    return repository.getTransferByDate(fromDate, toDate)
  }
}