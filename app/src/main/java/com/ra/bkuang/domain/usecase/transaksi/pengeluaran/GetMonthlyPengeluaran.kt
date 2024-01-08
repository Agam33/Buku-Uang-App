package com.ra.bkuang.domain.usecase.transaksi.pengeluaran

import com.ra.bkuang.data.local.entity.DetailPengeluaran
import com.ra.bkuang.domain.util.Resource
import com.ra.bkuang.domain.model.RvGroup
import kotlinx.coroutines.flow.Flow

interface GetMonthlyPengeluaran {
  fun invoke(month: Int, year: Int): Flow<Resource<RvGroup<String, ArrayList<DetailPengeluaran>>>>
}