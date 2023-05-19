package com.ra.budgetplan.domain.usecase.hutang.impl

import com.ra.budgetplan.domain.model.HutangModel
import com.ra.budgetplan.domain.repository.HutangRepository
import com.ra.budgetplan.domain.usecase.hutang.SaveHutang
import javax.inject.Inject

class SaveHutangImpl @Inject constructor(
  private val repository: HutangRepository
): SaveHutang {
  override suspend fun invoke(hutang: HutangModel) {
    repository.save(hutang)
  }
}