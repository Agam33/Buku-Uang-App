package com.ra.bkuang.features.debt.domain

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.debt.data.entity.DetailPembayaranHutang
import com.ra.bkuang.features.debt.domain.model.DetailPembayaranHutangModel
import com.ra.bkuang.features.debt.domain.model.PembayaranHutangModel
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface PembayaranHutangRepository {
  fun getSizeListPembayaranHutangById(id: UUID): Flow<Int?>
  fun findAllRecordByHutangId(id: String): Flow<Result<List<DetailPembayaranHutangModel>>>
  fun save(pembayaranHutang: PembayaranHutangModel): Flow<Result<Boolean>>
  fun delete(pembayaranHutang: PembayaranHutangModel): Flow<Result<Boolean>>
  fun update(pembayaranHutang: PembayaranHutangModel): Flow<Result<Boolean>>
}