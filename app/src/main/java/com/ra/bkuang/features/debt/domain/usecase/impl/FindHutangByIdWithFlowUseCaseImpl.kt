package com.ra.bkuang.features.debt.domain.usecase.impl

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.debt.domain.repository.HutangRepository
import com.ra.bkuang.features.debt.data.model.HutangModel
import com.ra.bkuang.features.debt.domain.usecase.FindHutangByIdWithFlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FindHutangByIdWithFlowUseCaseImpl @Inject constructor(
  private val hutangRepository: HutangRepository
): FindHutangByIdWithFlowUseCase {
  override fun invoke(id: String): Flow<Result<HutangModel?>> {
    return hutangRepository.findByIdWithFlow(id)
  }
}