package com.ra.bkuang.features.debt.domain.usecase.impl

import com.ra.bkuang.features.debt.domain.repository.HutangRepository
import com.ra.bkuang.features.debt.domain.model.HutangModel
import com.ra.bkuang.features.debt.domain.usecase.SetAlarmDebtUseCase
import java.util.Calendar
import javax.inject.Inject

class SetAlarmDebtUseCaseImpl @Inject constructor(
  private val hutangRepository: HutangRepository,
  ): SetAlarmDebtUseCase {
  override suspend operator fun invoke(
    calendar: Calendar,
    model: HutangModel
  ): Boolean  {
    return hutangRepository.setAlarmDebt(calendar, model)
  }
}