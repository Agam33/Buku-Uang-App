package com.ra.bkuang.features.analytics.domain.usecase.impl

import com.ra.bkuang.common.util.Resource
import com.ra.bkuang.features.analytics.domain.usecase.DetailAnalytics
import com.ra.bkuang.features.transaction.data.mapper.toModel
import com.ra.bkuang.features.transaction.domain.PendapatanRepository
import com.ra.bkuang.features.transaction.domain.PengeluaranRepository
import com.ra.bkuang.features.transaction.domain.model.TransactionDetail
import com.ra.bkuang.features.transaction.presentation.TransactionType
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