package com.ra.bkuang.features.debt.domain.usecase.impl

import com.ra.bkuang.common.di.IoDispatcherQualifier
import com.ra.bkuang.features.debt.domain.repository.HutangRepository
import com.ra.bkuang.features.debt.data.model.HutangModel
import com.ra.bkuang.features.debt.domain.usecase.FindHutangByIdUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class FindHutangByIdUseCaseImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispatcher: CoroutineDispatcher,
  private val hutangRepository: HutangRepository
): FindHutangByIdUseCase {
  override suspend fun invoke(id: String): HutangModel = withContext(ioDispatcher) {
    return@withContext hutangRepository.findById(UUID.fromString(id))
  }
}