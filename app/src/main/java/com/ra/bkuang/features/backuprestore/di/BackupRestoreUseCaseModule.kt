package com.ra.bkuang.features.backuprestore.di

import com.ra.bkuang.features.backuprestore.domain.usecase.CreateLocalBackupUseCase
import com.ra.bkuang.features.backuprestore.domain.usecase.GetLocalBackupUseCase
import com.ra.bkuang.features.backuprestore.domain.usecase.impl.CreateLocalBackupUseCaseImpl
import com.ra.bkuang.features.backuprestore.domain.usecase.impl.GetLocalBackupUseCaseImpl
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
  fun bindCreateLocalBackup(createLocalBackupImpl: CreateLocalBackupUseCaseImpl): CreateLocalBackupUseCase

  @Binds
  @Singleton
  fun bindGetLocalRestore(getLocalRestoreImpl: GetLocalBackupUseCaseImpl): GetLocalBackupUseCase
}