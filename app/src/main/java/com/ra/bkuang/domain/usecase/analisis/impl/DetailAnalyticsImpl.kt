package com.ra.bkuang.domain.usecase.analisis.impl

import com.ra.bkuang.domain.mapper.toModel
import com.ra.bkuang.domain.repository.PendapatanRepository
import com.ra.bkuang.domain.repository.PengeluaranRepository
import com.ra.bkuang.domain.usecase.analisis.DetailAnalytics
import com.ra.bkuang.domain.util.Resource
import com.ra.bkuang.presentation.ui.features.transaction.TransactionDetail
import com.ra.bkuang.presentation.ui.features.transaction.TransactionType
import java.time.LocalDateTime
import javax.inject.Inject

class DetailAnalyticsImpl @Inject constructor(
  private val pengeluaranRepository: PengeluaranRepository,
  private val pendapatanRepository: PendapatanRepository
): DetailAnalytics {

  override suspend fun invoke(
    transactionType: TransactionType,
    fromDate: LocalDateTime,
    toDate: LocalDateTime
  ): Resource<List<TransactionDetail>> =
    when(transactionType) {
      TransactionType.INCOME -> {
        val incomes = pendapatanRepository.getPendapatanByDate(fromDate, toDate).map { it.toModel() }

        if(incomes.isEmpty()) {
         Resource.Empty("")
        } else {
          Resource.Success(incomes)
        }
      }

      TransactionType.EXPENSE -> {
        val expenseList = pengeluaranRepository.getPengeluaranByDate(fromDate, toDate).map { it.toModel() }

        if(expenseList.isEmpty()) {
          Resource.Empty("")
        } else {
          Resource.Success(expenseList)
        }
      }
      TransactionType.TRANSFER -> Resource.Empty("")
    }
}