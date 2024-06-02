package com.ra.bkuang.features.account.domain.usecase.impl

import com.ra.bkuang.common.util.Result
import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.account.domain.AkunRepository
import com.ra.bkuang.features.account.domain.model.AkunModel
import com.ra.bkuang.features.account.domain.usecase.DeleteAkun
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteAkunImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispatcher: CoroutineDispatcher,
  private val akunRepository: AkunRepository
): DeleteAkun {
  override operator fun invoke(akun: AkunModel): Flow<Result<Boolean>> {
    return akunRepository.delete(akun)
  }
}