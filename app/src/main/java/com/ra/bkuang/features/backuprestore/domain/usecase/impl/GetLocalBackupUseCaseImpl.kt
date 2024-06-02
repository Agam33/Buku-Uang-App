package com.ra.bkuang.features.backuprestore.domain.usecase.impl

import android.net.Uri
import com.ra.bkuang.features.backuprestore.domain.BackupRestoreRepository
import com.ra.bkuang.features.backuprestore.domain.usecase.GetLocalBackupUseCase
import javax.inject.Inject

class GetLocalBackupUseCaseImpl @Inject constructor(
  private val backupRestoreRepository: BackupRestoreRepository
): GetLocalBackupUseCase {
  override suspend operator fun invoke(uri: Uri, dbDirectory: String): Boolean {
    return backupRestoreRepository.getLocalBackupDb(uri, dbDirectory)
  }
}