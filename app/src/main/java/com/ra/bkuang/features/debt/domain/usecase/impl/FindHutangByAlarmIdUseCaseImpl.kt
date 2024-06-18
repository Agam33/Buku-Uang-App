package com.ra.bkuang.features.debt.domain.usecase.impl

import com.ra.bkuang.features.debt.domain.repository.HutangRepository
import com.ra.bkuang.features.debt.domain.model.HutangModel
import com.ra.bkuang.features.debt.domain.usecase.FindHutangByAlarmIdUseCase
import javax.inject.Inject

class FindHutangByAlarmIdUseCaseImpl @Inject constructor(
  private val hutangRepository: HutangRepository
): FindHutangByAlarmIdUseCase {
  override suspend fun invoke(alarmId: Int): HutangModel  {
    return hutangRepository.findByAlarmId(alarmId)
  }
}