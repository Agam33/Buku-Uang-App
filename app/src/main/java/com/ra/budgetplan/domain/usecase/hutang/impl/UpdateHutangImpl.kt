package com.ra.budgetplan.domain.usecase.hutang.impl

import com.ra.budgetplan.domain.mapper.toEntity
import com.ra.budgetplan.domain.model.HutangModel
import com.ra.budgetplan.domain.repository.HutangRepository
import com.ra.budgetplan.domain.usecase.hutang.UpdateHutang
import javax.inject.Inject

class UpdateHutangImpl @Inject constructor(
  private val repository: HutangRepository
): UpdateHutang {
  override suspend fun invoke(hutang: HutangModel) {
    repository.update(hutang.toEntity())
  }
}