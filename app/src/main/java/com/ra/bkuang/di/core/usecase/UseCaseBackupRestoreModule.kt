package com.ra.bkuang.di.core.usecase

import com.ra.bkuang.domain.usecase.backuprestore.BackupDb
import com.ra.bkuang.domain.usecase.backuprestore.RestoreDb
import com.ra.bkuang.domain.usecase.backuprestore.impl.BackupDbImpl
import com.ra.bkuang.domain.usecase.backuprestore.impl.RestoreDbImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseBackupRestoreModule {

  @Binds
  @Singleton
  fun bindBackUpDb(backupDbImpl: BackupDbImpl): BackupDb

  @Binds
  @Singleton
  fun bindRestoreDb(restoreDbImpl: RestoreDbImpl): RestoreDb
}