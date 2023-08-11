package com.ra.budgetplan.domain.usecase.hutang

import com.ra.budgetplan.domain.model.DetailPembayaranHutangModel
import com.ra.budgetplan.util.StatusItem
import kotlinx.coroutines.flow.Flow

interface DeleteRecordPembayaranHutang {
  suspend fun invoke(detailPembayaranHutangModel: DetailPembayaranHutangModel): Flow<StatusItem>
}