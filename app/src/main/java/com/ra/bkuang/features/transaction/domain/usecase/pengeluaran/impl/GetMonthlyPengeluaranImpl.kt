package com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.impl

import com.ra.bkuang.common.util.Resource
import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.transaction.data.entity.DetailPengeluaran
import com.ra.bkuang.features.transaction.domain.PengeluaranRepository
import com.ra.bkuang.features.transaction.domain.model.TransactionGroup
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.GetMonthlyPengeluaran
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters
import java.util.Calendar
import javax.inject.Inject

class GetMonthlyPengeluaranImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispather: CoroutineDispatcher,
  private val repository: PengeluaranRepository
): GetMonthlyPengeluaran {

  override fun invoke(month: Int, year: Int): Flow<Resource<TransactionGroup<String, ArrayList<DetailPengeluaran>>>>  {
    return flow<Resource<TransactionGroup<String, ArrayList<DetailPengeluaran>>>> {
      val calendar = Calendar.getInstance()
      calendar.set(Calendar.YEAR, year)
      calendar.set(Calendar.MONTH, month - 1)
      val localDateTime = LocalDateTime.ofInstant(calendar.toInstant(), ZoneId.systemDefault())

      repository.getMonthlyPengeluaran(
        localDateTime.withDayOfMonth(1).withHour(0).withMinute(0),
        localDateTime.with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59)
      ).collect {
        if (it.isEmpty()) {
          emit(Resource.Empty(""))
        } else {
          val monthly = TransactionGroup<String, ArrayList<DetailPengeluaran>>()
          for (data in it) {
            val updatedAt = data.pengeluaran.createdAt
            val key = updatedAt.toLocalDate().toString()
            monthly.addIf(key, ArrayList())?.add(data)
          }
          emit(Resource.Success(monthly))
        }
      }
    }.flowOn(ioDispather)
  }
}