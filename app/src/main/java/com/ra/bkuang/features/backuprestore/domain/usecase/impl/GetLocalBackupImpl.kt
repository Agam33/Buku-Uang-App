package com.ra.bkuang.features.backuprestore.domain.usecase.impl

import android.net.Uri
import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.backuprestore.domain.BackupRestoreRepository
import com.ra.bkuang.features.backuprestore.domain.usecase.GetLocalBackup
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetLocalBackupImpl @Inject constructor(
  @IoDispatcherQualifier private val ioDispatcher: CoroutineDispatcher,
  private val backupRestoreRepository: BackupRestoreRepository
): GetLocalBackup {
  override suspend fun invoke(uri: Uri, dbDirectory: String): Boolean = withContext(ioDispatcher) {
    try {
      backupRestoreRepository.getLocalBackupDb(uri, dbDirectory)
      return@withContext true
    } catch (e: Exception) {
      return@withContext false
    }
  }
}