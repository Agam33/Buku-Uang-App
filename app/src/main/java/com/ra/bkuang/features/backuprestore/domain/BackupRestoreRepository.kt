package com.ra.bkuang.features.backuprestore.domain

import android.net.Uri
import java.io.IOException

interface BackupRestoreRepository {
  @Throws(IOException::class)
  suspend fun createLocalBackupDb(fileName: String, destDirectory: String)

  @Throws(IOException::class)
  suspend fun getLocalBackupDb(uriFile: Uri, dest: String)
}