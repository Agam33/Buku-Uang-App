package com.ra.budgetplan.domain.usecase.backuprestore.impl

import com.ra.budgetplan.data.local.database.AppDatabase
import com.ra.budgetplan.domain.usecase.backuprestore.RestoreDb
import com.ra.budgetplan.util.StatusItem
import com.ra.budgetplan.util.unZipFile
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import java.io.IOException
import javax.inject.Inject

class RestoreDbImpl @Inject constructor(
  private val db: AppDatabase
): RestoreDb {
  override fun invoke(src: File, dest: File): Flow<StatusItem> {
    return flow {
      db.close()
      emit(StatusItem.LOADING)

      if(src.exists()) {
        try {
          unZipFile(src, dest)
          delay(500)
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