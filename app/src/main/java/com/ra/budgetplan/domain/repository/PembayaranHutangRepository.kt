package com.ra.budgetplan.domain.repository

import com.ra.budgetplan.domain.entity.DetailPembayaranHutang
import com.ra.budgetplan.domain.entity.PembayaranHutangEntity
import java.util.UUID

interface PembayaranHutangRepository {
  suspend fun findAllRecordByHutangId(id: UUID): List<DetailPembayaranHutang>
  suspend fun save(pembayaranHutang: PembayaranHutangEntity)
  suspend fun delete(pembayaranHutang: PembayaranHutangEntity)
  suspend fun update(pembayaranHutang: PembayaranHutangEntity)
}