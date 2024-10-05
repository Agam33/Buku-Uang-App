package com.ra.bkuang.features.debt.domain.usecase.impl

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.debt.domain.repository.HutangRepository
import com.ra.bkuang.features.debt.data.model.HutangModel
import com.ra.bkuang.features.debt.domain.usecase.CreateHutangUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateHutangUseCaseImpl @Inject constructor(
  private val hutangRepository: HutangRepository
): CreateHutangUseCase {
  override operator fun invoke(hutangModel: HutangModel): Flow<Result<Boolean>> {
      return hutangRepository.save(hutangModel)
  }
}