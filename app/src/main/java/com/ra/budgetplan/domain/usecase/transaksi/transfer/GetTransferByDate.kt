package com.ra.budgetplan.domain.usecase.transaksi.transfer

import com.ra.budgetplan.domain.entity.DetailTransfer
import com.ra.budgetplan.util.Resource
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface GetTransferByDate {
  fun invoke(fromDate: LocalDateTime, toDate: LocalDateTime): Flow<Resource<List<DetailTransfer>>>
}