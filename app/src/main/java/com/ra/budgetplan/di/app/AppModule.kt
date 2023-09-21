package com.ra.budgetplan.di.app

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.ra.budgetplan.util.FILE_USER_SETTING_PREF
import com.ra.budgetplan.util.FILE_USER_SETTING_SHARED_PREF
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
object AppModule {

  @Provides
  @Singleton
  fun provideDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
    return PreferenceDataStoreFactory.create(
      scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
      produceFile = { appContext.preferencesDataStoreFile(FILE_USER_SETTING_PREF) }
    )
  }

  @Provides
  @Singleton
  fun provideSharedPreferences(@ApplicationContext appContext: Context): SharedPreferences {
    return appContext.getSharedPreferences(FILE_USER_SETTING_SHARED_PREF, Context.MODE_PRIVATE)
  }
}