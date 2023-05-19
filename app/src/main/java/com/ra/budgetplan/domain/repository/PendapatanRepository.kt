package com.ra.budgetplan.domain.repository

import com.ra.budgetplan.domain.model.PendapatanModel

interface PendapatanRepository {
  suspend fun save(pendapatan: PendapatanModel)
  suspend fun delete(pendapatan: PendapatanModel)
  suspend fun update(pendapatan: PendapatanModel)
}