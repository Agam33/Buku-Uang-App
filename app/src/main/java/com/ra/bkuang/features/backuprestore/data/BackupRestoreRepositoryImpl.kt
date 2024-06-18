package com.ra.bkuang.features.backuprestore.data

import android.net.Uri
import com.ra.bkuang.features.backuprestore.domain.repository.BackupRestoreRepository
import javax.inject.Inject

class BackupRestoreRepositoryImpl @Inject constructor(
  private val localBackRestoreManager: LocalBackRestoreManager
): BackupRestoreRepository {
  override suspend fun createLocalBackupDb(fileName: String, destDirectory: String): Boolean {
    return try {
      localBackRestoreManager.createLocalBackup(fileName, destDirectory)
    } catch (e: Exception) {
      false
    }
  }

  override suspend fun getLocalBackupDb(uriFile: Uri, dest: String): Boolean {
    return try {
      localBackRestoreManager.getLocalBackup(uriFile, dest)
      true
    } catch (e: Exception) {
      false
    }
  }
}