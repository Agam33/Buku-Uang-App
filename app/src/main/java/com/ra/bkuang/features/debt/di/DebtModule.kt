package com.ra.bkuang.features.debt.di

import com.ra.bkuang.features.debt.alarm.DebtAlarmManager
import com.ra.bkuang.features.debt.alarm.DebtAlarmManagerManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DebtModule {

  @Binds
  @Singleton
  fun bindDebtAlarmManager(debtAlarmManagerImpl: DebtAlarmManagerManagerImpl): DebtAlarmManager
}

