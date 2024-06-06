package com.ra.bkuang.features.analytics.domain.usecase.impl

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.analytics.domain.model.AnalyticModel
import com.ra.bkuang.features.analytics.domain.usecase.ShowAnalyticListUseCase
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.GetListDetailPendapatanByDateUseCase
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.GetTotalPendapatanByDateUseCase
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.GetListDetailPengeluaranByDateUseCase
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.GetTotalPengeluaranByDateUseCase
import com.ra.bkuang.features.transaction.presentation.TransactionType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class ShowAnalyticListUseCaseImpl @Inject constructor(
  private val getListDetailPendapatanByDateUseCase: GetListDetailPendapatanByDateUseCase,
  private val getListDetailPengeluaranByDateUseCase: GetListDetailPengeluaranByDateUseCase,
  private val getTotalPengeluaranByDateUseCase: GetTotalPengeluaranByDateUseCase,
  private val getTotalPendapatanByDateUseCase: GetTotalPendapatanByDateUseCase,
): ShowAnalyticListUseCase {

  override operator fun invoke(
    transactionType: TransactionType,
    fromDate: LocalDateTime,
    toDate: LocalDateTime
  ): Flow<Result<List<AnalyticModel>>> {
    return flow {
      when(transactionType) {
        TransactionType.INCOME -> {
          val incomes = getListDetailPendapatanByDateUseCase.invoke(fromDate, toDate)
          val totalIncome = getTotalPendapatanByDateUseCase.invoke(fromDate, toDate)

          if(incomes.isEmpty()) {
            emit(Result.Error("Income is Empty"))
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

            emit(Result.Success(analyticModels))
          }
        }
        TransactionType.EXPENSE -> {
          val expenses = getListDetailPengeluaranByDateUseCase.invoke(fromDate, toDate)
          val totalExpense = getTotalPengeluaranByDateUseCase.invoke(fromDate, toDate)

          if(expenses.isEmpty()) {
            emit(Result.Error("expenses is Empty"))
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

            emit(Result.Success(analyticModels))
          }
        }
        TransactionType.TRANSFER -> { emit(Result.Error("TransactionType not found")) }
      }
    }
  }

  private fun calculatePercent(currValue: Int, maxValue: Long): Double =
    ((currValue * 1.0) / (maxValue * 1.0 )) * 100.0
}