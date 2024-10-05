package com.ra.bkuang.features.account.domain.usecase.impl

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.account.domain.repository.AkunRepository
import com.ra.bkuang.features.account.data.model.AkunModel
import com.ra.bkuang.features.account.domain.usecase.SaveAkunUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveAkunUseCaseImpl @Inject constructor(
  private val repository: AkunRepository
): SaveAkunUseCase {

  override operator fun invoke(akun: AkunModel): Flow<Result<Boolean>> {
    return repository.save(akun)
  }
}