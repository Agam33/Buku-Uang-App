package com.ra.bkuang.features.backuprestore.domain.usecase

interface CreateLocalBackupUseCase {
  suspend fun invoke(fileName: String, destDirectory: String): Boolean
}