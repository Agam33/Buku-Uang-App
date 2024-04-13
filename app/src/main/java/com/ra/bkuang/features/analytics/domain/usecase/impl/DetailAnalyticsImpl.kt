package com.ra.bkuang.features.analytics.domain.usecase.impl

import com.ra.bkuang.common.util.Resource
import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.analytics.domain.usecase.DetailAnalytics
import com.ra.bkuang.features.transaction.data.mapper.toModel
import com.ra.bkuang.features.transaction.domain.model.TransactionDetail
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.GetListDetailPendapatanByDate
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.GetListDetailPengeluaranByDate
import com.ra.bkuang.features.transaction.presentation.TransactionType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject

class DetailAnalyticsImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispather: CoroutineDispatcher,
  private val getListDetailPendapatanByDate: GetListDetailPendapatanByDate,
  private val getListDetailPengeluaranByDate: GetListDetailPengeluaranByDate
): DetailAnalytics {

  override suspend fun invoke(
    transactionType: TransactionType,
    fromDate: LocalDateTime,
    toDate: LocalDateTime
  ): Resource<List<TransactionDetail>> = withContext(ioDispather) {
    when (transactionType) {
      TransactionType.INCOME -> {
        val incomes = getListDetailPendapatanByDate.invoke(fromDate, toDate).map { it.toModel() }

        if (incomes.isEmpty()) {
          return@withContext Resource.Empty("")
        } else {
          return@withContext Resource.Success(incomes)
        }
      }

      TransactionType.EXPENSE -> {
        val expenseList = getListDetailPengeluaranByDate.invoke(fromDate, toDate).map { it.toModel() }

        if (expenseList.isEmpty()) {
          return@withContext Resource.Empty("")
        } else {
          return@withContext Resource.Success(expenseList)
        }
      }

      TransactionType.TRANSFER -> return@withContext Resource.Empty("")
    }
  }
}