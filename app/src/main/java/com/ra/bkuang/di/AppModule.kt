package com.ra.bkuang.di

import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.ra.bkuang.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultCoroutineScope

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoCoroutineScope

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DataStorePrefName

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SharedPreferenceName

@Qualifier
@Retention
annotation class AppNotificationManager

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

  @AppNotificationManager
  @Provides
  @Singleton
  fun provideNotificationManager(
    @ApplicationContext ctx: Context
  ): NotificationManager = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

  @DefaultDispatcher
  @Singleton
  @Provides
  fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

  @IoDispatcher
  @Singleton
  @Provides
  fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

  @IoCoroutineScope
  @Singleton
  @Provides
  fun provideIoScope(
    @IoDispatcher ioDispatcher: CoroutineDispatcher
  ): CoroutineScope = CoroutineScope(ioDispatcher + SupervisorJob())

  @DefaultDispatcher
  @Singleton
  @Provides
  fun provideDefaultScope(
    @DefaultDispatcher defaultDispatcher: CoroutineDispatcher
  ): CoroutineScope = CoroutineScope(defaultDispatcher + SupervisorJob())

  @SharedPreferenceName
  @Singleton
  @Provides
  fun provideSharedPreferencesFileName(): String = "user_setting_pref.pb"

  @DataStorePrefName
  @Singleton
  @Provides
  fun provideDataStoreFileName(): String = "user_setting_shared_pref"

  @Provides
  @Singleton
  fun provideDataStore(
    @ApplicationContext appContext: Context,
    @DataStorePrefName dataStoreName: String,
    @IoCoroutineScope ioScope: CoroutineScope
  ): DataStore<Preferences> {
    return PreferenceDataStoreFactory.create(
      scope = ioScope,
      produceFile = { appContext.preferencesDataStoreFile(dataStoreName) }
    )
  }

  @Provides
  @Singleton
  fun provideSharedPreferences(
    @ApplicationContext appContext: Context,
    @SharedPreferenceName sharedPreferenceName: String
  ): SharedPreferences {
    return appContext.getSharedPreferences(sharedPreferenceName, Context.MODE_PRIVATE)
  }
}