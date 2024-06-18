package com.ra.bkuang.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.ra.bkuang.common.di.DataStorePrefNameQualifier
import com.ra.bkuang.common.di.IoCoroutineScopeQualifier
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

  @DataStorePrefNameQualifier
  @Singleton
  @Provides
  fun provideDataStoreFileName(): String = "user_setting_shared_pref"

  @Provides
  @Singleton
  fun provideDataStore(
    @ApplicationContext appContext: Context,
    @DataStorePrefNameQualifier dataStoreName: String,
    @IoCoroutineScopeQualifier ioScope: CoroutineScope
  ): DataStore<Preferences> {
    return PreferenceDataStoreFactory.create(
      scope = ioScope,
      produceFile = { appContext.preferencesDataStoreFile(dataStoreName) }
    )
  }
}