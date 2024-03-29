package com.ra.bkuang.di

import com.ra.bkuang.data.preferences.UserSettingPref
import com.ra.bkuang.data.preferences.UserSettingPrefImpl
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