package com.ra.bkuang.domain.usecase.hutang

import com.ra.bkuang.domain.model.DetailPembayaranHutangModel
import com.ra.bkuang.util.Resource
import java.util.UUID

interface FindAllRecordPembayaranHutang {
  suspend fun invoke(id: String): Resource<List<DetailPembayaranHutangModel>>
}