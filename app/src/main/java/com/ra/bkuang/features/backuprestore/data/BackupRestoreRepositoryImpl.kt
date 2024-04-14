package com.ra.bkuang.features.backuprestore.data

import android.net.Uri
import com.ra.bkuang.features.backuprestore.domain.BackupRestoreRepository
import javax.inject.Inject

class BackupRestoreRepositoryImpl @Inject constructor(
  private val localBackRestoreManager: LocalBackRestoreManager
): BackupRestoreRepository {
  override suspend fun createLocalBackupDb(fileName: String, destDirectory: String) {
    localBackRestoreManager.createLocalBackup(fileName, destDirectory)
  }

  override suspend fun getLocalBackupDb(uriFile: Uri, dest: String) {
     localBackRestoreManager.getLocalBackup(uriFile, dest)
  }
}