package com.ra.bkuang.features.backuprestore.domain.usecase

import android.net.Uri

interface GetLocalBackupUseCase {
  suspend operator fun invoke(uri: Uri, dbDirectory: String): Boolean
}