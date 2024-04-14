package com.ra.bkuang.features.backuprestore.di

import com.ra.bkuang.features.backuprestore.domain.usecase.CreateLocalBackup
import com.ra.bkuang.features.backuprestore.domain.usecase.GetLocalBackup
import com.ra.bkuang.features.backuprestore.domain.usecase.impl.CreateLocalBackupImpl
import com.ra.bkuang.features.backuprestore.domain.usecase.impl.GetLocalBackupImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface BackupRestoreUseCaseModule {
  @Binds
  @Singleton
  fun bindCreateLocalBackup(createLocalBackupImpl: CreateLocalBackupImpl): CreateLocalBackup

  @Binds
  @Singleton
  fun bindGetLocalRestore(getLocalRestoreImpl: GetLocalBackupImpl): GetLocalBackup
}