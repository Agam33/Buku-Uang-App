package com.ra.bkuang.features.backuprestore.data

import android.net.Uri
import java.io.IOException

interface LocalBackRestoreManager {

  @Throws(IOException::class)
  suspend fun createLocalBackup(fileName: String, destDirectory: String): Boolean

  @Throws(IOException::class)
  suspend fun getLocalBackup(uri: Uri, directory: String)
}