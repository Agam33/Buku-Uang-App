package com.ra.bkuang.features.transaction.domain.usecase.pengeluaran

import com.ra.bkuang.common.util.Resource
import com.ra.bkuang.features.transaction.data.entity.DetailPengeluaran
import com.ra.bkuang.features.transaction.domain.model.TransactionGroup
import kotlinx.coroutines.flow.Flow

interface GetMonthlyPengeluaran {
  fun invoke(month: Int, year: Int): Flow<Resource<TransactionGroup<String, ArrayList<DetailPengeluaran>>>>
}