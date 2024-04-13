package com.ra.bkuang.features.debt.di

import com.ra.bkuang.features.debt.alarm.DebtAlarmManager
import com.ra.bkuang.features.debt.alarm.DebtAlarmManagerManagerImpl
import com.ra.bkuang.features.debt.presentation.adapter.DebtAdapter
import com.ra.bkuang.features.debt.presentation.adapter.DebtRecordAdapter
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DebtModule {

  @Binds
  @Singleton
  fun bindDebtAlarmManager(debtAlarmManagerImpl: DebtAlarmManagerManagerImpl): DebtAlarmManager
}

@Module
@InstallIn(FragmentComponent::class)
object DebtFragmentModule {
  @Provides
  fun provideListDebtAdapter(): DebtAdapter = DebtAdapter()
}

@Module
@InstallIn(ActivityComponent::class)
object DetailDebtModule {
  @Provides
  fun provideListDebtRecordAdapter(): DebtRecordAdapter = DebtRecordAdapter()
}

