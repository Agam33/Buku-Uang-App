package com.ra.bkuang.features.account.domain.usecase.impl

import com.ra.bkuang.features.account.domain.AkunRepository
import com.ra.bkuang.features.account.domain.model.AkunModel
import com.ra.bkuang.features.account.domain.usecase.FindAllAkunWithFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FindAllAkunWithFlowImpl @Inject constructor(
  private val repository: AkunRepository
): FindAllAkunWithFlow {

  override operator fun invoke(): Flow<List<AkunModel>> {
    return repository.findAllWithFlow()
  }
}