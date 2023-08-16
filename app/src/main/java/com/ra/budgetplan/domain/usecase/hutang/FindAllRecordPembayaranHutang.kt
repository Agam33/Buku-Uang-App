package com.ra.budgetplan.domain.usecase.hutang

import com.ra.budgetplan.domain.model.DetailPembayaranHutangModel
import com.ra.budgetplan.util.Resource
import java.util.UUID

interface FindAllRecordPembayaranHutang {
  suspend fun invoke(id: UUID): Resource<List<DetailPembayaranHutangModel>>
}