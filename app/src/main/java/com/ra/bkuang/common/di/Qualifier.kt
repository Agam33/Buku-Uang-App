package com.ra.bkuang.common.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SharedPreferenceNameQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppNotificationManagerQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppAlarmManagerQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoCoroutineScopeQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DataStorePrefNameQualifier

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
annotation class DBNameQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DBSeederQualifier