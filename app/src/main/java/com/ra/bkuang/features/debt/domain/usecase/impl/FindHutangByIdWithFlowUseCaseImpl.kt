package com.ra.bkuang.features.debt.domain.usecase.impl

import com.ra.bkuang.features.debt.domain.HutangRepository
import com.ra.bkuang.features.debt.domain.model.HutangModel
import com.ra.bkuang.features.debt.domain.usecase.FindHutangByIdWithFlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FindHutangByIdWithFlowUseCaseImpl @Inject constructor(
  private val hutangRepository: HutangRepository
): FindHutangByIdWithFlowUseCase {
  override fun invoke(id: String): Flow<HutangModel?> {
    return hutangRepository.findByIdWithFlow(id)
  }
}