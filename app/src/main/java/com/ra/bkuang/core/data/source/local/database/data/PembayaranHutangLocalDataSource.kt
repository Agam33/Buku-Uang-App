package com.ra.bkuang.core.data.source.local.database.data

import com.ra.bkuang.core.data.source.local.database.entity.DetailPembayaranHutang
import com.ra.bkuang.core.data.source.local.database.entity.PembayaranHutangEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface PembayaranHutangLocalDataSource {
  fun getSizeListPembayaranHutangById(id: UUID): Flow<Int?>
  fun findAllRecordByHutangId(id: UUID): Flow<List<DetailPembayaranHutang>>
  suspend fun save(pembayaranHutang: PembayaranHutangEntity)
  suspend fun delete(pembayaranHutang: PembayaranHutangEntity)
  suspend fun update(pembayaranHutang: PembayaranHutangEntity)
}