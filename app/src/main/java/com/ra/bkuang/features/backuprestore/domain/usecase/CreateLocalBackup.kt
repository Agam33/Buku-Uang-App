package com.ra.bkuang.features.backuprestore.domain.usecase

interface CreateLocalBackup {
  suspend fun invoke(fileName: String, destDirectory: String): Boolean
}