package com.ra.bkuang.features.debt.data.local

import com.ra.bkuang.features.debt.data.entity.DetailPembayaranHutang
import com.ra.bkuang.features.debt.data.entity.PembayaranHutangEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface PembayaranHutangLocalDataSource {
  fun getSizeListPembayaranHutangById(id: UUID): Flow<Int?>
  suspend fun findAllRecordByHutangId(id: UUID): List<DetailPembayaranHutang>
  suspend fun save(pembayaranHutang: PembayaranHutangEntity)
  suspend fun delete(pembayaranHutang: PembayaranHutangEntity)
  suspend fun update(pembayaranHutang: PembayaranHutangEntity)
}