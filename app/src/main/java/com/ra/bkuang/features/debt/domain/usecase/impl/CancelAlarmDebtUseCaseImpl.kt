package com.ra.bkuang.features.debt.domain.usecase.impl

import com.ra.bkuang.di.IoCoroutineScopeQualifier
import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.debt.alarm.DebtAlarmManager
import com.ra.bkuang.features.debt.domain.model.HutangModel
import com.ra.bkuang.features.debt.domain.usecase.CancelAlarmDebtUseCase
import com.ra.bkuang.features.debt.domain.usecase.UpdateHutangUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CancelAlarmDebtUseCaseImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispatcher: CoroutineDispatcher,
  @IoCoroutineScopeQualifier private val ioScope: CoroutineScope,
  private val alarm: DebtAlarmManager,
  private val updateHutangUseCase: UpdateHutangUseCase
): CancelAlarmDebtUseCase {
  override suspend fun invoke(model: HutangModel) = withContext(ioDispatcher) {
    return@withContext alarm.cancelAlarm(model) {
      ioScope.launch {
        updateHutangUseCase.invoke(it)
      }
    }
  }
}