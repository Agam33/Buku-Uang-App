package com.ra.bkuang.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {

  @DefaultDispatcherQualifier
  @Singleton
  @Provides
  fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

  @MainDispatcherQualifier
  @Singleton
  @Provides
  fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

  @IoDispatcherQualifier
  @Singleton
  @Provides
  fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

}