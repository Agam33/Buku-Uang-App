package com.ra.bkuang.features.account.domain.usecase.impl

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.account.domain.repository.AkunRepository
import com.ra.bkuang.features.account.domain.model.AkunModel
import com.ra.bkuang.features.account.domain.usecase.DeleteAkunUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteAkunUseCaseImpl @Inject constructor(
  private val akunRepository: AkunRepository
): DeleteAkunUseCase {
  override operator fun invoke(akun: AkunModel): Flow<Result<Boolean>> {
    return akunRepository.delete(akun)
  }
}