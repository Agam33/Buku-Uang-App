package com.ra.budgetplan.domain.usecase.backuprestore.impl

import com.ra.budgetplan.domain.usecase.backuprestore.BackupDb
import com.ra.budgetplan.util.DB_NAME
import com.ra.budgetplan.util.StatusItem
import com.ra.budgetplan.util.zipFiles
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.io.File
import java.io.IOException
import javax.inject.Inject

class BackupDbImpl @Inject constructor() : BackupDb {
  override fun invoke(src: File, dest: File): Flow<StatusItem> {
    return flow {
      emit(StatusItem.LOADING)

      val dbShm = File(src.parent, "$DB_NAME-shm")
      val dbWal = File(src.parent, "$DB_NAME-wal")

      if(src.exists() && dbShm.exists() && dbWal.exists()) {
        try {
          zipFiles(listOf(src, dbShm, dbWal), dest)
          emit(StatusItem.SUCCESS)
        } catch (e: IOException) {
          emit(StatusItem.FAILED)
        }
      } else {
        emit(StatusItem.FAILED)
      }
    }
  }
}