package com.ra.budgetplan.domain.usecase.transaksi.pengeluaran.impl

import com.ra.budgetplan.domain.entity.DetailPengeluaran
import com.ra.budgetplan.domain.repository.PengeluaranRepository
import com.ra.budgetplan.domain.usecase.transaksi.pengeluaran.GetPengeluaranByDate
import com.ra.budgetplan.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.time.LocalDateTime
import javax.inject.Inject

class GetPengeluaranByDateImpl @Inject constructor(
  private val repository: PengeluaranRepository
): GetPengeluaranByDate {

  override fun invoke(fromDate: LocalDateTime, toDate: LocalDateTime): Flow<Resource<List<DetailPengeluaran>>> {
    return flow {
      repository.getPengeluaranByDate(fromDate, toDate).collect {
        if(it.isEmpty()) emit(Resource.Empty(""))
        else {
          emit(Resource.Success(it))
        }
      }
    }
  }
}