package com.ra.budgetplan.domain.usecase.hutang

import com.ra.budgetplan.domain.model.PembayaranHutangModel
import com.ra.budgetplan.util.StatusItem
import kotlinx.coroutines.flow.Flow

interface SavePembayaranHutang {
  suspend fun invoke(pembayaranHutangModel: PembayaranHutangModel): Flow<StatusItem>
}