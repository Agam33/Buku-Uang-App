package com.ra.budgetplan.data.local.preferences

import com.ra.budgetplan.presentation.ui.transaction.adapter.DateViewType
import kotlinx.coroutines.flow.Flow

interface UserSettingPref {
  fun getDateViewType(): Flow<String>
  suspend fun saveDateViewType(dateViewType: DateViewType)
  fun saveFileBackupDirectory(filePath: String)
  fun getFileBackupDirectory(): String?
}