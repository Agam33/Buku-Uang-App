package com.ra.bkuang.features.account.domain.usecase.impl

import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.account.domain.AkunRepository
import com.ra.bkuang.features.account.domain.model.AkunModel
import com.ra.bkuang.features.account.domain.usecase.UpdateAkun
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateAkunImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispatcher: CoroutineDispatcher,
  private val repository: AkunRepository
): UpdateAkun {
  override suspend fun invoke(akun: AkunModel) = withContext(ioDispatcher) {
    return@withContext repository.update(akun)
  }
}