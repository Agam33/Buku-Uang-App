package com.ra.budgetplan.domain.usecase.backuprestore.impl

import com.ra.budgetplan.domain.usecase.backuprestore.BackupDb
import com.ra.budgetplan.util.Constants.DB_NAME
import com.ra.budgetplan.util.Constants.zipFiles
import com.ra.budgetplan.util.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import java.io.IOException
import javax.inject.Inject

class BackupDbImpl @Inject constructor() : BackupDb {
  override fun invoke(src: File, dest: File): Flow<ResourceState> {
    return flow {
      emit(ResourceState.LOADING)

      val dbShm = File(src.parent, "$DB_NAME-shm")
      val dbWal = File(src.parent, "$DB_NAME-wal")

      if(src.exists() && dbShm.exists() && dbWal.exists()) {
        try {
          zipFiles(listOf(src, dbShm, dbWal), dest)
          emit(ResourceState.SUCCESS)
        } catch (e: IOException) {
          emit(ResourceState.FAILED)
        }
      } else {
        emit(ResourceState.FAILED)
      }
    }
  }
}