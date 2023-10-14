package com.ra.bkuang.domain.usecase.backuprestore

import com.ra.bkuang.util.ResourceState
import kotlinx.coroutines.flow.Flow
import java.io.File

interface RestoreDb {
  fun invoke(src: File, dest: File): Flow<ResourceState>
}