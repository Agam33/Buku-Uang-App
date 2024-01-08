package com.ra.bkuang.presentation.viewmodel

import com.ra.bkuang.domain.util.ResourceState
import com.ra.bkuang.presentation.util.Constants
import com.ra.bkuang.presentation.util.Constants.DB_NAME
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import java.io.IOException

class BackupRestoreViewModel: BaseViewModel() {

  fun doBackup(src: File, dest: File): Flow<ResourceState> {
    return flow {
      emit(ResourceState.LOADING)

      val dbShm = File(src.parent, "$DB_NAME-shm")
      val dbWal = File(src.parent, "$DB_NAME-wal")

      if(src.exists() && dbShm.exists() && dbWal.exists()) {
        try {
          Constants.zipFiles(listOf(src, dbShm, dbWal), dest)
          emit(ResourceState.SUCCESS)
        } catch (e: IOException) {
          emit(ResourceState.FAILED)
        }
      } else {
        emit(ResourceState.FAILED)
      }
    }
  }


  fun doRestore(src: File, dest: File): Flow<ResourceState> {
    return flow {
      emit(ResourceState.LOADING)

      if(src.exists()) {
        try {
          Constants.unZipFile(src, dest)
          delay(500)
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