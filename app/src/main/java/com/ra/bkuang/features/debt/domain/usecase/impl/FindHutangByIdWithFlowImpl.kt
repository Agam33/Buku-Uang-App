package com.ra.bkuang.features.debt.domain.usecase.impl

import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.debt.data.mapper.toModel
import com.ra.bkuang.features.debt.domain.HutangRepository
import com.ra.bkuang.features.debt.domain.model.HutangModel
import com.ra.bkuang.features.debt.domain.usecase.FindHutangByIdWithFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class FindHutangByIdWithFlowImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispatcher: CoroutineDispatcher,
  private val hutangRepository: HutangRepository
): FindHutangByIdWithFlow {
  override fun invoke(id: String): Flow<HutangModel?> {
    return hutangRepository.findByIdWithFlow(UUID.fromString(id))
  }
}