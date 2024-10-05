package com.ra.bkuang.features.debt.domain.usecase.impl

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.debt.domain.repository.HutangRepository
import com.ra.bkuang.features.debt.data.model.HutangModel
import com.ra.bkuang.features.debt.domain.usecase.UpdateHutangUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateHutangUseCaseImpl @Inject constructor(
  private val hutangRepository: HutangRepository
): UpdateHutangUseCase {
  override operator fun invoke(hutangModel: HutangModel): Flow<Result<Boolean>> {
   return hutangRepository.update(hutangModel)
  }
}