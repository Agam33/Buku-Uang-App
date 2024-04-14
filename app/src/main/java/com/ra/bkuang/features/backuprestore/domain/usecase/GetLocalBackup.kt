package com.ra.bkuang.features.backuprestore.domain.usecase

import android.net.Uri

interface GetLocalBackup {
  suspend fun invoke(uri: Uri, dbDirectory: String): Boolean
}