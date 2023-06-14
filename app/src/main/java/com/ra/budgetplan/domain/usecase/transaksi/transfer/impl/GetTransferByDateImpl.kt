package com.ra.budgetplan.domain.usecase.transaksi.transfer.impl

import com.ra.budgetplan.domain.entity.DetailTransfer
import com.ra.budgetplan.domain.repository.TransferRepository
import com.ra.budgetplan.domain.usecase.transaksi.transfer.GetTransferByDate
import com.ra.budgetplan.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import javax.inject.Inject

class GetTransferByDateImpl @Inject constructor(
  private val repository: TransferRepository
): GetTransferByDate {
  override fun invoke(fromDate: LocalDateTime, toDate: LocalDateTime): Flow<Resource<List<DetailTransfer>>> {
    return flow {
      repository.getTransferByDate(fromDate, toDate).collect {
        if(it.isEmpty()) emit(Resource.Empty(""))
        else emit(Resource.Success(it))
      }
    }
  }
}