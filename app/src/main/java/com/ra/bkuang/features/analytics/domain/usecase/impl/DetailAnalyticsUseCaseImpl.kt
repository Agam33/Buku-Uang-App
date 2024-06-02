package com.ra.bkuang.features.analytics.domain.usecase.impl

import com.ra.bkuang.common.util.ResultState
import com.ra.bkuang.features.analytics.domain.usecase.DetailAnalyticsUseCase
import com.ra.bkuang.features.transaction.data.mapper.toModel
import com.ra.bkuang.features.transaction.domain.model.TransactionDetail
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.GetListDetailPendapatanByDateUseCase
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.GetListDetailPengeluaranByDateUseCase
import com.ra.bkuang.features.transaction.presentation.TransactionType
import java.time.LocalDateTime
import javax.inject.Inject

class DetailAnalyticsUseCaseImpl @Inject constructor(
  private val getListDetailPendapatanByDateUseCase: GetListDetailPendapatanByDateUseCase,
  private val getListDetailPengeluaranByDateUseCase: GetListDetailPengeluaranByDateUseCase
): DetailAnalyticsUseCase {

  override suspend fun invoke(
    transactionType: TransactionType,
    fromDate: LocalDateTime,
    toDate: LocalDateTime
  ): ResultState<List<TransactionDetail>> {
    when (transactionType) {
      TransactionType.INCOME -> {
        val incomes = getListDetailPendapatanByDateUseCase.invoke(fromDate, toDate).map { it.toModel() }

        if (incomes.isEmpty()) {
          return ResultState.Empty
        } else {
          return ResultState.Success(incomes)
        }
      }

      TransactionType.EXPENSE -> {
        val expenseList = getListDetailPengeluaranByDateUseCase.invoke(fromDate, toDate).map { it.toModel() }

        if (expenseList.isEmpty()) {
          return ResultState.Empty
        } else {
          return ResultState.Success(expenseList)
        }
      }

      TransactionType.TRANSFER -> return ResultState.Empty
    }
  }
}