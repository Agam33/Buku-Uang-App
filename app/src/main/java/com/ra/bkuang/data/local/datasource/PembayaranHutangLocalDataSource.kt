package com.ra.bkuang.data.local.datasource

import com.ra.bkuang.data.local.database.entity.DetailPembayaranHutang
import com.ra.bkuang.data.local.database.entity.PembayaranHutangEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface PembayaranHutangLocalDataSource {
  fun getSizeListPembayaranHutangById(id: UUID): Flow<Int?>
  suspend fun findAllRecordByHutangId(id: UUID): List<DetailPembayaranHutang>
  suspend fun save(pembayaranHutang: PembayaranHutangEntity)
  suspend fun delete(pembayaranHutang: PembayaranHutangEntity)
  suspend fun update(pembayaranHutang: PembayaranHutangEntity)
}