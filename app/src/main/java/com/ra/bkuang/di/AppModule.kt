package com.ra.bkuang.di

import android.app.AlarmManager
import android.app.NotificationManager
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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
}