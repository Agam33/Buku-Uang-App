package com.ra.bkuang.features.transaction.domain.usecase.pengeluaran

import com.ra.bkuang.common.util.ResultState
import com.ra.bkuang.features.transaction.data.entity.DetailPengeluaran
import com.ra.bkuang.features.transaction.domain.model.TransactionGroup
import kotlinx.coroutines.flow.Flow

interface GetMonthlyPengeluaranUseCase {
  fun invoke(month: Int, year: Int): Flow<ResultState<TransactionGroup<String, ArrayList<DetailPengeluaran>>>>
}