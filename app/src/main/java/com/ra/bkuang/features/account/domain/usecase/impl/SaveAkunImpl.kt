package com.ra.bkuang.features.account.domain.usecase.impl

import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.account.domain.AkunRepository
import com.ra.bkuang.features.account.domain.model.AkunModel
import com.ra.bkuang.features.account.domain.usecase.SaveAkun
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveAkunImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispatcher: CoroutineDispatcher,
  private val repository: AkunRepository
): SaveAkun {

  override suspend fun invoke(akun: AkunModel) = withContext(ioDispatcher) {
    return@withContext repository.save(akun)
  }
}