package com.ra.bkuang.features.transaction.domain.usecase.transfer

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.transaction.data.entity.DetailTransfer
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface GetTransferByDateUseCase {
  fun invoke(fromDate: LocalDateTime, toDate: LocalDateTime): Flow<Result<List<DetailTransfer>>>
}