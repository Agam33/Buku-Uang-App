package com.ra.bkuang.domain.usecase.hutang.impl

import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.domain.mapper.toModel
import com.ra.bkuang.domain.model.HutangModel
import com.ra.bkuang.domain.repository.HutangRepository
import com.ra.bkuang.domain.usecase.hutang.FindHutangByAlarmId
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FindHutangByAlarmIdImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispatcher: CoroutineDispatcher,
  private val hutangRepository: HutangRepository
): FindHutangByAlarmId {
  override suspend fun invoke(alarmId: Int): HutangModel = withContext(ioDispatcher) {
    hutangRepository.findByAlarmId(alarmId).toModel()
  }
}