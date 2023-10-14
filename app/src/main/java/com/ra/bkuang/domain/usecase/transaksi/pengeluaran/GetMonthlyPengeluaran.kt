package com.ra.bkuang.domain.usecase.transaksi.pengeluaran

import com.ra.bkuang.domain.entity.DetailPengeluaran
import com.ra.bkuang.util.Resource
import com.ra.bkuang.util.RvGroup
import kotlinx.coroutines.flow.Flow

interface GetMonthlyPengeluaran {
  fun invoke(month: Int, year: Int): Flow<Resource<RvGroup<String, ArrayList<DetailPengeluaran>>>>
}