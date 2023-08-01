package com.ra.budgetplan.presentation.viewmodel

import com.ra.budgetplan.domain.usecase.backuprestore.BackupDb
import com.ra.budgetplan.domain.usecase.backuprestore.RestoreDb
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject

@HiltViewModel
class BackupRestoreViewModel @Inject constructor(
  private val backupDb: BackupDb,
  private val restoreDb: RestoreDb
): BaseViewModel() {

  fun doBackup(src: File, dest: File) =
    backupDb.invoke(src, dest)

  fun doRestore(src: File, dest: File) =
    restoreDb.invoke(src, dest)
}