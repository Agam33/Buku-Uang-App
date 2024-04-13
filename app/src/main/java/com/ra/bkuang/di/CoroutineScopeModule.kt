package com.ra.bkuang.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoroutineScopeModule {

  @IoCoroutineScopeQualifier
  @Singleton
  @Provides
  fun provideIoScope(
    @IoDispatcherQualifier ioDispatcherQualifier: CoroutineDispatcher
  ): CoroutineScope = CoroutineScope(ioDispatcherQualifier + SupervisorJob())

  @DefaultDispatcherQualifier
  @Singleton
  @Provides
  fun provideDefaultScope(
    @DefaultDispatcherQualifier defaultDispatcherQualifier: CoroutineDispatcher
  ): CoroutineScope = CoroutineScope(defaultDispatcherQualifier + SupervisorJob())
}