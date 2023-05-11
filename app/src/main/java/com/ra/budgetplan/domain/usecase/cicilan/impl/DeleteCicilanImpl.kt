package com.ra.budgetplan.domain.usecase.cicilan.impl

import com.ra.budgetplan.domain.model.CicilanModel
import com.ra.budgetplan.domain.repository.CicilanRepository
import com.ra.budgetplan.domain.usecase.cicilan.DeleteCicilan
import javax.inject.Inject

class DeleteCicilanImpl @Inject constructor(
  private val repository: CicilanRepository
): DeleteCicilan {
  override suspend fun invoke(cicilanModel: CicilanModel) {
    repository.delete(cicilanModel)
  }
}