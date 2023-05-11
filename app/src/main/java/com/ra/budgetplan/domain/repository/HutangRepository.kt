package com.ra.budgetplan.domain.repository

import com.ra.budgetplan.domain.model.HutangModel

interface HutangRepository {
  suspend fun save(hutang: HutangModel)
  suspend fun delete(hutang: HutangModel)
  suspend fun update(hutang: HutangModel)
}