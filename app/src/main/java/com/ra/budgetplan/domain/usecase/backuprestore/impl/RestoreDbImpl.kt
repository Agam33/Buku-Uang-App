package com.ra.budgetplan.domain.usecase.backuprestore.impl

import com.ra.budgetplan.domain.usecase.backuprestore.RestoreDb
import com.ra.budgetplan.util.Constants.unZipFile
import com.ra.budgetplan.util.ResourceState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import java.io.IOException
import javax.inject.Inject

class RestoreDbImpl @Inject constructor(): RestoreDb {
  override fun invoke(src: File, dest: File): Flow<ResourceState> {
    return flow {
      emit(ResourceState.LOADING)

      if(src.exists()) {
        try {
          unZipFile(src, dest)
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