package com.ra.budgetplan.domain.repository

import com.ra.budgetplan.domain.entity.PendapatanEntity

interface PendapatanRepository {
  suspend fun save(pendapatan: PendapatanEntity)
  suspend fun delete(pendapatan: PendapatanEntity)
  suspend fun update(pendapatan: PendapatanEntity)
}