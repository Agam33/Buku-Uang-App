package com.ra.bkuang.features.debt.domain.usecase

import com.ra.bkuang.features.debt.domain.model.DetailPembayaranHutangModel
import com.ra.bkuang.common.util.ResultState

interface FindAllRecordPembayaranHutang {
  suspend fun invoke(id: String): ResultState<List<DetailPembayaranHutangModel>>
}