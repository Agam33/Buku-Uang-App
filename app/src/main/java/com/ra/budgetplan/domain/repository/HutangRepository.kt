package com.ra.budgetplan.domain.repository

import com.ra.budgetplan.domain.entity.HutangEntity

interface HutangRepository {
  suspend fun save(hutang: HutangEntity)
  suspend fun delete(hutang: HutangEntity)
  suspend fun update(hutang: HutangEntity)
}