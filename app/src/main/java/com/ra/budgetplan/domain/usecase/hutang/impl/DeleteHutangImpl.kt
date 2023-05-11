package com.ra.budgetplan.domain.usecase.hutang.impl

import com.ra.budgetplan.domain.model.HutangModel
import com.ra.budgetplan.domain.repository.HutangRepository
import com.ra.budgetplan.domain.usecase.hutang.DeleteHutang
import javax.inject.Inject

class DeleteHutangImpl @Inject constructor(
  private val repository: HutangRepository
): DeleteHutang {
  override suspend fun invoke(hutang: HutangModel) {
    repository.delete(hutang)
  }
}