package com.ra.budgetplan.domain.repository

import com.ra.budgetplan.domain.model.PengeluaranModel

interface PengeluaranRepository {
  suspend fun save(pengeluaran: PengeluaranModel)
  suspend fun delete(pengeluaran: PengeluaranModel)
  suspend fun update(pengeluaran: PengeluaranModel)
}