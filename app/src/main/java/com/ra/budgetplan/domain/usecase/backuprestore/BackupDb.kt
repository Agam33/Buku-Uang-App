package com.ra.budgetplan.domain.usecase.backuprestore

import com.ra.budgetplan.util.ResourceState
import kotlinx.coroutines.flow.Flow
import java.io.File

interface BackupDb {
  fun invoke(src: File, dest: File): Flow<ResourceState>
}