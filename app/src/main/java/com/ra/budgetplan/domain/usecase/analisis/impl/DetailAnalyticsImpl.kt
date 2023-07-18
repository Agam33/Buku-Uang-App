package com.ra.budgetplan.domain.usecase.analisis.impl

import com.ra.budgetplan.domain.mapper.toModel
import com.ra.budgetplan.domain.repository.PendapatanRepository
import com.ra.budgetplan.domain.repository.PengeluaranRepository
import com.ra.budgetplan.domain.usecase.analisis.DetailAnalytics
import com.ra.budgetplan.presentation.ui.transaction.TransactionDetail
import com.ra.budgetplan.presentation.ui.transaction.TransactionType
import com.ra.budgetplan.util.Resource
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