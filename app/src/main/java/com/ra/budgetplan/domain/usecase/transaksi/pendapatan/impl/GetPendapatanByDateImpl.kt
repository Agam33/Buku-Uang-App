package com.ra.budgetplan.domain.usecase.transaksi.pendapatan.impl

import com.ra.budgetplan.domain.entity.DetailPendapatan
import com.ra.budgetplan.domain.repository.PendapatanRepository
import com.ra.budgetplan.domain.usecase.transaksi.pendapatan.GetPendapatanByDate
import com.ra.budgetplan.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import javax.inject.Inject

class GetPendapatanByDateImpl @Inject constructor(
  private val repository: PendapatanRepository
): GetPendapatanByDate {
  override fun invoke(fromDate: LocalDateTime, toDate: LocalDateTime): Flow<Resource<List<DetailPendapatan>>> {
    return flow {
      repository.getPendapatanByDate(fromDate, toDate).collect {
        if(it.isEmpty()) emit(Resource.Empty(""))
        else emit(Resource.Success(it))
      }
    }
  }
}