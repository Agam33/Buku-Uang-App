package com.ra.bkuang.features.account.domain.usecase.impl

import com.ra.bkuang.features.account.domain.repository.AkunRepository
import com.ra.bkuang.features.account.data.model.AkunModel
import com.ra.bkuang.features.account.domain.usecase.FindAllAkunWithFlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FindAllAkunWithFlowUseCaseImpl @Inject constructor(
  private val repository: AkunRepository
): FindAllAkunWithFlowUseCase {

  override operator fun invoke(): Flow<List<AkunModel>> {
    return repository.findAllWithFlow()
  }
}