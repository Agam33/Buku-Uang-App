package com.ra.budgetplan.domain.usecase.cicilan.impl

import com.ra.budgetplan.domain.model.CicilanModel
import com.ra.budgetplan.domain.repository.CicilanRepository
import com.ra.budgetplan.domain.usecase.cicilan.FindAllCicilan
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FindAllCicilanImpl @Inject constructor(
  private val repository: CicilanRepository
): FindAllCicilan {
  override fun findAll(): Flow<List<CicilanModel>> {
    return repository.findAll()
  }
}