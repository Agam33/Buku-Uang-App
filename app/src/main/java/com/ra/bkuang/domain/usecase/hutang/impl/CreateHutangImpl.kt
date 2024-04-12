package com.ra.bkuang.domain.usecase.hutang.impl

import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.domain.mapper.toEntity
import com.ra.bkuang.domain.model.HutangModel
import com.ra.bkuang.domain.repository.HutangRepository
import com.ra.bkuang.domain.usecase.hutang.CreateHutang
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
      hutangRepository.save(hutangModel.toEntity())
      return@withContext true
    } catch (e: Exception) {
      Timber.e(e)
      return@withContext false
    }
  }
}