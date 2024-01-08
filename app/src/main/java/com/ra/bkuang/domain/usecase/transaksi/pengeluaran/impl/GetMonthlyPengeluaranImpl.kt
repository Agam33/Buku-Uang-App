package com.ra.bkuang.domain.usecase.transaksi.pengeluaran.impl

import com.ra.bkuang.data.local.entity.DetailPengeluaran
import com.ra.bkuang.domain.repository.PengeluaranRepository
import com.ra.bkuang.domain.usecase.transaksi.pengeluaran.GetMonthlyPengeluaran
import com.ra.bkuang.domain.util.Resource
import com.ra.bkuang.domain.model.RvGroup
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters
import java.util.Calendar
import javax.inject.Inject

class GetMonthlyPengeluaranImpl @Inject constructor(
  private val repository: PengeluaranRepository
): GetMonthlyPengeluaran {

  override fun invoke(month: Int, year: Int): Flow<Resource<RvGroup<String, ArrayList<DetailPengeluaran>>>> {
    return flow {
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
          val monthly = RvGroup<String, ArrayList<DetailPengeluaran>>()
          for (data in it) {
            val updatedAt = data.pengeluaran.createdAt
            val key = updatedAt.toLocalDate().toString()
            monthly.addIf(key, ArrayList())?.add(data)
          }
          emit(Resource.Success(monthly))
        }
      }
    }
  }
}