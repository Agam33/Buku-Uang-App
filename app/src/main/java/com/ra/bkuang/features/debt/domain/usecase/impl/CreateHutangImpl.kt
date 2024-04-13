package com.ra.bkuang.features.debt.domain.usecase.impl

import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.debt.data.mapper.toEntity
import com.ra.bkuang.features.debt.domain.HutangRepository
import com.ra.bkuang.features.debt.domain.model.HutangModel
import com.ra.bkuang.features.debt.domain.usecase.CreateHutang
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class CreateHutangImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispatcher: CoroutineDispatcher,
  private val hutangRepository: HutangRepository
): CreateHutang {
  override suspend fun invoke(hutangModel: HutangModel): Boolean = withContext(ioDispatcher) {
    try {
      hutangRepository.save(hutangModel)
      return@withContext true
    } catch (e: Exception) {
      Timber.e(e)
      return@withContext false
    }
  }
}