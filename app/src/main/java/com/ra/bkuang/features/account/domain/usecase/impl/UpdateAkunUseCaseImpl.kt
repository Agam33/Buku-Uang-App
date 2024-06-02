package com.ra.bkuang.features.account.domain.usecase.impl

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.account.domain.AkunRepository
import com.ra.bkuang.features.account.domain.model.AkunModel
import com.ra.bkuang.features.account.domain.usecase.UpdateAkunUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateAkunUseCaseImpl @Inject constructor(
  private val repository: AkunRepository
): UpdateAkunUseCase {
  override operator fun invoke(akun: AkunModel): Flow<Result<Boolean>> {
    return repository.update(akun)
  }
}