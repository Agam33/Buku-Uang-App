package com.ra.bkuang.features.backuprestore.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.backuprestore.domain.usecase.CreateLocalBackup
import com.ra.bkuang.features.backuprestore.domain.usecase.GetLocalBackup
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BackupRestoreViewModel @Inject constructor(
  @IoDispatcherQualifier private val ioDispatcher: CoroutineDispatcher,
  private val createLocalBackup: CreateLocalBackup,
  private val getLocalBackup: GetLocalBackup
): ViewModel() {

  suspend fun createLocalBackup(fileName: String, destDirectory: String): Boolean = withContext(ioDispatcher) {
    return@withContext createLocalBackup.invoke(fileName, destDirectory)
  }

  suspend fun getLocalBackup(uri: Uri, directoryDb: String): Boolean = withContext(ioDispatcher) {
    return@withContext getLocalBackup.invoke(uri, directoryDb)
  }
}