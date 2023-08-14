package com.ra.budgetplan.domain.usecase.hutang.impl

import com.ra.budgetplan.domain.mapper.toModel
import com.ra.budgetplan.domain.model.HutangModel
import com.ra.budgetplan.domain.repository.HutangRepository
import com.ra.budgetplan.domain.usecase.hutang.FindHutangById
import java.util.UUID
import javax.inject.Inject

class FindHutangByIdImpl @Inject constructor(
  private val hutangRepository: HutangRepository
): FindHutangById {
  override suspend fun invoke(id: UUID): HutangModel {
    return hutangRepository.findById(id).toModel()
  }
}