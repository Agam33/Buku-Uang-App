package com.ra.budgetplan.data.local

import com.ra.budgetplan.domain.entity.HutangEntity

interface HutangLocalDataSource {
  suspend fun save(hutang: HutangEntity)
  suspend fun delete(hutang: HutangEntity)
  suspend fun update(hutang: HutangEntity)
}