package com.ra.bkuang.features.account.domain.usecase.impl

import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.account.domain.AkunRepository
import com.ra.bkuang.features.account.domain.model.AkunModel
import com.ra.bkuang.features.account.domain.usecase.FindAllAkun
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FindAllAkunImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispatcher: CoroutineDispatcher,
  private val repository: AkunRepository
): FindAllAkun {
  override suspend fun invoke(): List<AkunModel> = withContext(ioDispatcher) {
    return@withContext repository.findAll()
  }
}