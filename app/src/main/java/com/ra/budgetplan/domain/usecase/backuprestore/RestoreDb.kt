package com.ra.budgetplan.domain.usecase.backuprestore

import com.ra.budgetplan.util.StatusItem
import kotlinx.coroutines.flow.Flow
import java.io.File

interface RestoreDb {
  fun invoke(src: File, dest: File): Flow<StatusItem>
}