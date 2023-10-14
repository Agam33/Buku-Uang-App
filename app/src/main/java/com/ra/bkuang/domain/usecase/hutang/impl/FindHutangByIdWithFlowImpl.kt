package com.ra.bkuang.domain.usecase.hutang.impl

import com.ra.bkuang.domain.mapper.toModel
import com.ra.bkuang.domain.model.HutangModel
import com.ra.bkuang.domain.repository.HutangRepository
import com.ra.bkuang.domain.usecase.hutang.FindHutangByIdWithFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class FindHutangByIdWithFlowImpl @Inject constructor(
  private val hutangRepository: HutangRepository
): FindHutangByIdWithFlow {
  override fun invoke(id: UUID): Flow<HutangModel> {
    return hutangRepository.findByIdWithFlow(id).map { it.toModel() }
  }
}