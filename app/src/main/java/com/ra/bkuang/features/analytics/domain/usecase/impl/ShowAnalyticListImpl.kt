package com.ra.bkuang.features.analytics.domain.usecase.impl

import com.ra.bkuang.common.util.Resource
import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.analytics.domain.model.AnalyticModel
import com.ra.bkuang.features.analytics.domain.usecase.ShowAnalyticList
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.GetListDetailPendapatanByDate
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.GetTotalPendapatanByDate
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.GetListDetailPengeluaranByDate
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.GetTotalPengeluaranByDate
import com.ra.bkuang.features.transaction.presentation.TransactionType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class ShowAnalyticListImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispather: CoroutineDispatcher,
  private val getListDetailPendapatanByDate: GetListDetailPendapatanByDate,
  private val getListDetailPengeluaranByDate: GetListDetailPengeluaranByDate,
  private val getTotalPengeluaranByDate: GetTotalPengeluaranByDate,
  private val getTotalPendapatanByDate: GetTotalPendapatanByDate,
): ShowAnalyticList {

  override suspend fun invoke(
    transactionType: TransactionType,
    fromDate: LocalDateTime,
    toDate: LocalDateTime
  ): Resource<List<AnalyticModel>> = withContext(ioDispather) {
    return@withContext when(transactionType) {
      TransactionType.INCOME -> {
        val incomes = getListDetailPendapatanByDate.invoke(fromDate, toDate)
        val totalIncome = getTotalPendapatanByDate.invoke(fromDate, toDate)

        if(incomes.isEmpty()) {
          Resource.Empty("")
        } else {
          val mapPendapatan = HashMap<UUID, AnalyticModel>()
          for(item in incomes) {
            val id = item.kategori.uuid
            val prevModel = mapPendapatan.getOrDefault(id, AnalyticModel(name = item.kategori.nama))
            prevModel.total += item.pendapatan.jumlah
            mapPendapatan[id] = prevModel
          }

          val analyticModels = ArrayList<AnalyticModel>()
          for(key in mapPendapatan.keys) {
            val model = mapPendapatan[key] ?: AnalyticModel()
            model.percent = calculatePercent(model.total, totalIncome)
            analyticModels.add(model)
          }

          analyticModels.sortByDescending { it.percent }

          Resource.Success(analyticModels)
        }
      }
      TransactionType.EXPENSE -> {
        val expenses = getListDetailPengeluaranByDate.invoke(fromDate, toDate)
        val totalExpense = getTotalPengeluaranByDate.invoke(fromDate, toDate)

        if(expenses.isEmpty()) {
          Resource.Empty("")
        } else {
          val mapPengeluaran = HashMap<UUID, AnalyticModel>()
          for(item in expenses) {
            val id = item.kategori.uuid
            val prevModel = mapPengeluaran.getOrDefault(id, AnalyticModel(name = item.kategori.nama))
            prevModel.total += item.pengeluaran.jumlah
            mapPengeluaran[id] = prevModel
          }

          val analyticModels = ArrayList<AnalyticModel>()
          for(key in mapPengeluaran.keys) {
            val model = mapPengeluaran[key] ?: AnalyticModel()
            model.percent = calculatePercent(model.total, totalExpense)
            analyticModels.add(model)
          }

          analyticModels.sortByDescending { it.percent }

          Resource.Success(analyticModels)
        }
      }
      TransactionType.TRANSFER -> { Resource.Empty("") }
    }
  }

  private fun calculatePercent(currValue: Int, maxValue: Long): Double =
    ((currValue * 1.0) / (maxValue * 1.0 )) * 100.0
}