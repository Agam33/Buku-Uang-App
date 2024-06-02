package com.ra.bkuang.features.backuprestore.domain.usecase.impl

import com.ra.bkuang.features.backuprestore.domain.BackupRestoreRepository
import com.ra.bkuang.features.backuprestore.domain.usecase.CreateLocalBackupUseCase
import java.io.IOException
import javax.inject.Inject

class CreateLocalBackupUseCaseImpl @Inject constructor(
  private val backupRestoreRepository: BackupRestoreRepository
): CreateLocalBackupUseCase {
  override suspend fun invoke(fileName: String, destDirectory: String): Boolean =
    try {
      backupRestoreRepository.createLocalBackupDb(fileName, destDirectory)
      true
    } catch (e: IOException) {
      false
    }
}