package com.ra.bkuang.presentation.di

import com.ra.bkuang.presentation.settings.UserSettingPref
import com.ra.bkuang.presentation.settings.UserSettingPrefImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface SettingModule {

  @Binds
  fun provideUserSettingPref(userSettingPrefImpl: UserSettingPrefImpl): UserSettingPref
}