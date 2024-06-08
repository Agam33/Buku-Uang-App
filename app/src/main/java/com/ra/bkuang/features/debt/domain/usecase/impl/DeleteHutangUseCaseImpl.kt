package com.ra.bkuang.features.debt.domain.usecase.impl

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.debt.domain.HutangRepository
import com.ra.bkuang.features.debt.domain.model.HutangModel
import com.ra.bkuang.features.debt.domain.usecase.DeleteHutangUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteHutangUseCaseImpl @Inject constructor(
  private val hutangRepository: HutangRepository
): DeleteHutangUseCase {
  override operator fun invoke(hutangModel: HutangModel): Flow<Result<Boolean>> {
    return hutangRepository.delete(hutangModel)
  }
}