package com.ra.budgetplan.domain.usecase.cicilan.impl

import com.ra.budgetplan.domain.mapper.toEntity
import com.ra.budgetplan.domain.model.CicilanModel
import com.ra.budgetplan.domain.repository.CicilanRepository
import com.ra.budgetplan.domain.usecase.cicilan.SaveCicilan
import javax.inject.Inject

class SaveCicilanImpl @Inject constructor(
  private val repository: CicilanRepository
): SaveCicilan {
  override suspend fun invoke(cicilanModel: CicilanModel) {
    repository.save(cicilanModel.toEntity())
  }
}