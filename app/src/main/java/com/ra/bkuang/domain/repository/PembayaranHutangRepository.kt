package com.ra.bkuang.domain.repository

import com.ra.bkuang.data.entity.DetailPembayaranHutang
import com.ra.bkuang.data.entity.PembayaranHutangEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface PembayaranHutangRepository {
  fun getSizeListPembayaranHutangById(id: UUID): Flow<Int?>
  suspend fun findAllRecordByHutangId(id: UUID): List<DetailPembayaranHutang>
  suspend fun save(pembayaranHutang: PembayaranHutangEntity)
  suspend fun delete(pembayaranHutang: PembayaranHutangEntity)
  suspend fun update(pembayaranHutang: PembayaranHutangEntity)
}