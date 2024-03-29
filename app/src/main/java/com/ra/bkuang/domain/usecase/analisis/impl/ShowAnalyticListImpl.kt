package com.ra.bkuang.domain.usecase.analisis.impl

import com.ra.bkuang.domain.model.AnalyticModel
import com.ra.bkuang.domain.repository.PendapatanRepository
import com.ra.bkuang.domain.repository.PengeluaranRepository
import com.ra.bkuang.domain.usecase.analisis.ShowAnalyticList
import com.ra.bkuang.util.Utils.calculatePercent
import com.ra.bkuang.util.Resource
import com.ra.bkuang.presentation.ui.transaction.TransactionType
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class ShowAnalyticListImpl @Inject constructor(
  private val pendapatanRepository: PendapatanRepository,
  private val pengeluaranRepository: PengeluaranRepository,
): ShowAnalyticList {

  override suspend fun invoke(
      transactionType: TransactionType,
      fromDate: LocalDateTime,
      toDate: LocalDateTime
  ): Resource<List<AnalyticModel>> {
    return when(transactionType) {
      TransactionType.INCOME -> {
        val incomes = pendapatanRepository.getPendapatanByDate(fromDate, toDate)
        val totalIncome = pendapatanRepository.getTotalPendapatan(fromDate, toDate) ?: 0

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
        val expenses = pengeluaranRepository.getPengeluaranByDate(fromDate, toDate)
        val totalExpense = pengeluaranRepository.getTotalPengeluaran(fromDate, toDate) ?: 0

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
}