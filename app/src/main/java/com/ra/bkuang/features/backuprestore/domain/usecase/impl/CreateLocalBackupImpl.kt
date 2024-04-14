package com.ra.bkuang.features.backuprestore.domain.usecase.impl

import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.backuprestore.domain.BackupRestoreRepository
import com.ra.bkuang.features.backuprestore.domain.usecase.CreateLocalBackup
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class CreateLocalBackupImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispatcher: CoroutineDispatcher,
  private val backupRestoreRepository: BackupRestoreRepository
): CreateLocalBackup {
  override suspend fun invoke(fileName: String, destDirectory: String): Boolean = withContext(ioDispatcher) {
    try {
      backupRestoreRepository.createLocalBackupDb(fileName, destDirectory)
      return@withContext true
    } catch (e: IOException) {
      return@withContext false
    }
  }
}