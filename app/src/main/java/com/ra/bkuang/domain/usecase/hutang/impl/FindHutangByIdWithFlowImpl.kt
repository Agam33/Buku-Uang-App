package com.ra.bkuang.domain.usecase.hutang.impl

import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.domain.mapper.toModel
import com.ra.bkuang.domain.model.HutangModel
import com.ra.bkuang.domain.repository.HutangRepository
import com.ra.bkuang.domain.usecase.hutang.FindHutangByIdWithFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

class FindHutangByIdWithFlowImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispatcher: CoroutineDispatcher,
  private val hutangRepository: HutangRepository
): FindHutangByIdWithFlow {
  override fun invoke(id: String): Flow<HutangModel?> {
    return hutangRepository.findByIdWithFlow(UUID.fromString(id)).map {
      it?.toModel()
    }.flowOn(ioDispatcher)
  }
}