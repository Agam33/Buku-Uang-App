package com.ra.bkuang.features.transaction.domain.usecase.transfer

import com.ra.bkuang.features.transaction.data.entity.DetailTransfer
import java.time.LocalDateTime

interface GetTransferByDateUseCase {
 suspend fun invoke(fromDate: LocalDateTime, toDate: LocalDateTime): List<DetailTransfer>
}