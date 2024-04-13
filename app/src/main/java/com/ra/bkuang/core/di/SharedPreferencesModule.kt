package com.ra.bkuang.core.di

import android.content.Context
import android.content.SharedPreferences
import com.ra.bkuang.di.SharedPreferenceNameQualifier
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule {

  @SharedPreferenceNameQualifier
  @Singleton
  @Provides
  fun provideSharedPreferencesFileName(): String = "user_setting_pref.pb"

  @Provides
  @Singleton
  fun provideSharedPreferences(
    @ApplicationContext appContext: Context,
    @SharedPreferenceNameQualifier sharedPreferenceNameQualifier: String
  ): SharedPreferences {
    return appContext.getSharedPreferences(sharedPreferenceNameQualifier, Context.MODE_PRIVATE)
  }
}