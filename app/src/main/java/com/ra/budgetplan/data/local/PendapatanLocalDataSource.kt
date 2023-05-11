package com.ra.budgetplan.data.local

import com.ra.budgetplan.domain.entity.PendapatanEntity

interface PendapatanLocalDataSource {
  suspend fun savePendapatan(pendapatan: PendapatanEntity)
  suspend fun deletePendapatan(pendapatan: PendapatanEntity)
  suspend fun updatePendapatan(pendapatan: PendapatanEntity)
}