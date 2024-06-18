package com.ra.bkuang.features.analytics.data

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.analytics.domain.repository.AnalyticsRepository
import com.ra.bkuang.features.analytics.domain.model.AnalyticModel
import com.ra.bkuang.features.transaction.data.local.PendapatanLocalDataSource
import com.ra.bkuang.features.transaction.data.local.PengeluaranLocalDataSource
import com.ra.bkuang.features.transaction.data.mapper.toModel
import com.ra.bkuang.features.transaction.domain.model.TransactionDetail
import com.ra.bkuang.features.transaction.presentation.TransactionType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class AnalyticsRepositoryImpl @Inject constructor(
  @IoDispatcherQualifier private val  ioDispatcher: CoroutineDispatcher,
  private val pengeluaranLocalDataSource: PengeluaranLocalDataSource,
  private val pendapatanLocalDataSource: PendapatanLocalDataSource
): AnalyticsRepository {
  override fun showAnalyticList(transactionType: TransactionType, fromDate: LocalDateTime, toDate: LocalDateTime): Flow<Result<List<AnalyticModel>>> {
    return flow {
      when(transactionType) {
        TransactionType.INCOME -> {
          val incomes = pendapatanLocalDataSource.getPendapatanByDate(fromDate, toDate).first()
          val totalIncome = pendapatanLocalDataSource.getTotalPendapatanByDateWithFlow(fromDate, toDate).first() ?: 0

          if(incomes.isEmpty()) {
            emit(Result.Error("Income is Empty"))
            return@flow
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
          val expenses = pengeluaranLocalDataSource.getPengeluaranByDate(fromDate, toDate).first()
          val totalExpense = pengeluaranLocalDataSource.getTotalPengeluaranByDate(fromDate, toDate).first() ?: 0

          if(expenses.isEmpty()) {
            emit(Result.Error("expenses is Empty"))
            return@flow
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
    }.flowOn(ioDispatcher)
  }

  override fun detailAnalytics(transactionType: TransactionType, fromDate: LocalDateTime, toDate: LocalDateTime): Flow<Result<List<TransactionDetail>>> {
   return flow {
     when (transactionType) {
       TransactionType.INCOME -> {
         val incomes = pendapatanLocalDataSource.getPendapatanByDate(fromDate, toDate).first().map { it.toModel() }

         if (incomes.isEmpty()) {
           emit(Result.Error("List is Empty"))
           return@flow
         } else {
           emit(Result.Success(incomes))
         }
       }

       TransactionType.EXPENSE -> {
         val expenseList = pengeluaranLocalDataSource.getPengeluaranByDate(fromDate, toDate).first().map { it.toModel() }

         if (expenseList.isEmpty()) {
           emit(Result.Error("List is Empty"))
           return@flow
         } else {
           emit(Result.Success(expenseList))
         }
       }

       TransactionType.TRANSFER -> emit(Result.Error("TransactionType not found"))
     }
   }.flowOn(ioDispatcher)
  }

  private fun calculatePercent(currValue: Int, maxValue: Long): Double =
    ((currValue * 1.0) / (maxValue * 1.0 )) * 100.0
}