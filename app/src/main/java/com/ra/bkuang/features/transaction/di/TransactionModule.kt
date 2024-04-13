package com.ra.bkuang.features.transaction.di

import com.ra.bkuang.features.transaction.alarm.TransactionAlarmManager
import com.ra.bkuang.features.transaction.alarm.TransactionAlarmManagerManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface TransactionModule {

  @Binds
  @Singleton
  fun bindAlarmTransactionManager(transactionAlarmManagerImpl: TransactionAlarmManagerManagerImpl): TransactionAlarmManager
}
