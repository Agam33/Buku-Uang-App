package com.ra.budgetplan.data.local

import com.ra.budgetplan.domain.entity.PengeluaranEntity

interface PengeluaranLocalDataSource {
  suspend fun savePengeluaran(pengeluaran: PengeluaranEntity)
  suspend fun deletePengeluaran(pengeluaran: PengeluaranEntity)
  suspend fun updatePengeluaran(pengeluaran: PengeluaranEntity)
}