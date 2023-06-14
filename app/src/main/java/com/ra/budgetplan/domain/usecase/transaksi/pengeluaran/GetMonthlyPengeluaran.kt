package com.ra.budgetplan.domain.usecase.transaksi.pengeluaran

import com.ra.budgetplan.domain.entity.DetailPengeluaran
import com.ra.budgetplan.util.Resource
import com.ra.budgetplan.util.RvGroup
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface GetMonthlyPengeluaran {
  fun invoke(month: Int, year: Int): Flow<Resource<RvGroup<String, ArrayList<DetailPengeluaran>>>>
}