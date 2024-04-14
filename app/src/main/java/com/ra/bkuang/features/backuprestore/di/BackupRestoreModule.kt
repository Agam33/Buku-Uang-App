package com.ra.bkuang.features.backuprestore.di

import android.content.Context
import com.ra.bkuang.features.backuprestore.data.LocalBackRestoreManager
import com.ra.bkuang.features.backuprestore.data.LocalBackupRestoreManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object BackupRestoreModule {

  @Provides
  fun provideLocalBackupRestoreManager(
    @ApplicationContext ctx: Context
  ): LocalBackRestoreManager = LocalBackupRestoreManagerImpl(ctx)
}