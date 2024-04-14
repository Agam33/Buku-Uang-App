package com.ra.bkuang.features.debt.domain

import com.ra.bkuang.features.debt.data.entity.DetailPembayaranHutang
import com.ra.bkuang.features.debt.domain.model.PembayaranHutangModel
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface PembayaranHutangRepository {
  fun getSizeListPembayaranHutangById(id: UUID): Flow<Int?>
  suspend fun findAllRecordByHutangId(id: UUID): List<DetailPembayaranHutang>
  suspend fun save(pembayaranHutang: PembayaranHutangModel)
  suspend fun delete(pembayaranHutang: PembayaranHutangModel)
  suspend fun update(pembayaranHutang: PembayaranHutangModel)
}