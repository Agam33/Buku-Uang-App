package com.ra.budgetplan.domain.repository

import com.ra.budgetplan.domain.entity.PengeluaranEntity

interface PengeluaranRepository {
  suspend fun save(pengeluaran: PengeluaranEntity)
  suspend fun delete(pengeluaran: PengeluaranEntity)
  suspend fun update(pengeluaran: PengeluaranEntity)
}