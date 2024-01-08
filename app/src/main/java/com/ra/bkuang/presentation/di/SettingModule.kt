package com.ra.bkuang.presentation.di

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.ra.bkuang.presentation.settings.UserSettingPref
import com.ra.bkuang.presentation.settings.UserSettingPrefImpl
import com.ra.bkuang.presentation.util.Constants
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface SettingModule {

  @Binds
  fun provideUserSettingPref(userSettingPrefImpl: UserSettingPrefImpl): UserSettingPref
}