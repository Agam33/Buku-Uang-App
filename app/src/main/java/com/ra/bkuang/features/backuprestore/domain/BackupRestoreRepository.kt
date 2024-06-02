package com.ra.bkuang.features.backuprestore.domain

import android.net.Uri
import java.io.IOException

interface BackupRestoreRepository {
  suspend fun createLocalBackupDb(fileName: String, destDirectory: String): Boolean

  suspend fun getLocalBackupDb(uriFile: Uri, dest: String): Boolean
}