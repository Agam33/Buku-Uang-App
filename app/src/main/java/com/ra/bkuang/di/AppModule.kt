package com.ra.bkuang.di

import android.app.AlarmManager
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
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
annotation class MainDispatcherQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcherQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultDispatcherQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultCoroutineScopeQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoCoroutineScopeQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DataStorePrefNameQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SharedPreferenceNameQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppNotificationManagerQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppAlarmManagerQualifier

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

  @AppAlarmManagerQualifier
  @Provides
  @Singleton
  fun provideAppAlarmManager(@ApplicationContext ctx: Context): AlarmManager =
    ctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager

  @AppNotificationManagerQualifier
  @Provides
  @Singleton
  fun provideNotificationManager(
    @ApplicationContext ctx: Context
  ): NotificationManager = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

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

  @SharedPreferenceNameQualifier
  @Singleton
  @Provides
  fun provideSharedPreferencesFileName(): String = "user_setting_pref.pb"

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

  @Provides
  @Singleton
  fun provideSharedPreferences(
    @ApplicationContext appContext: Context,
    @SharedPreferenceNameQualifier sharedPreferenceNameQualifier: String
  ): SharedPreferences {
    return appContext.getSharedPreferences(sharedPreferenceNameQualifier, Context.MODE_PRIVATE)
  }
}