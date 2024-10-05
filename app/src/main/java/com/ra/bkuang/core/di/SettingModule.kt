package com.ra.bkuang.core.di

import com.ra.bkuang.core.data.source.local.preferences.UserSettingPref
import com.ra.bkuang.core.data.source.local.preferences.UserSettingPrefImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface SettingModule {

  @Binds
  @Singleton
  fun provideUserSettingPref(userSettingPrefImpl: UserSettingPrefImpl): UserSettingPref
}