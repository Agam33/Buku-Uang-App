package com.ra.bkuang.domain.usecase.transaksi.transfer

import com.ra.bkuang.domain.entity.DetailTransfer
import java.time.LocalDateTime

interface GetTransferByDate {
 suspend fun invoke(fromDate: LocalDateTime, toDate: LocalDateTime): List<DetailTransfer>
}