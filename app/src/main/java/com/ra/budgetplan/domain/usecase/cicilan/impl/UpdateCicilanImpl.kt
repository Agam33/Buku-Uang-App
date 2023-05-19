package com.ra.budgetplan.domain.usecase.cicilan.impl

import com.ra.budgetplan.domain.model.CicilanModel
import com.ra.budgetplan.domain.repository.CicilanRepository
import com.ra.budgetplan.domain.usecase.cicilan.UpdateCicilan
import javax.inject.Inject

class UpdateCicilanImpl @Inject constructor(
  private val repository: CicilanRepository
): UpdateCicilan {
  override suspend fun invoke(cicilanModel: CicilanModel) {
    repository.update(cicilanModel)
  }
}