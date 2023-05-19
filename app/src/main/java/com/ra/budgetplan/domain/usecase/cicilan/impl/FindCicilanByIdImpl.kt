package com.ra.budgetplan.domain.usecase.cicilan.impl

import com.ra.budgetplan.domain.model.CicilanModel
import com.ra.budgetplan.domain.repository.CicilanRepository
import com.ra.budgetplan.domain.usecase.cicilan.FindCicilanById
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class FindCicilanByIdImpl @Inject constructor(
  private val repository: CicilanRepository
): FindCicilanById {
  override fun invoke(id: UUID): Flow<CicilanModel> {
    return repository.findById(id)
  }
}