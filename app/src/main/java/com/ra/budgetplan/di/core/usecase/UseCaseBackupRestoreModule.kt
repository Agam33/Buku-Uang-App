package com.ra.budgetplan.di.core.usecase

import com.ra.budgetplan.domain.usecase.backuprestore.BackupDb
import com.ra.budgetplan.domain.usecase.backuprestore.RestoreDb
import com.ra.budgetplan.domain.usecase.backuprestore.impl.BackupDbImpl
import com.ra.budgetplan.domain.usecase.backuprestore.impl.RestoreDbImpl
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