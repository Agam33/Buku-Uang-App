package com.ra.bkuang.data.preferences

import com.ra.bkuang.util.DateViewType
import kotlinx.coroutines.flow.Flow

interface UserSettingPref {
  fun isActiveAlarmTransaction(): Flow<Boolean>
  suspend fun setAlarmTransaction(state: Boolean)
  fun getTextAlarmTransaction(): Flow<String>
  suspend fun setTextAlarmTransaction(text: String)
  fun getDateViewType(): Flow<String>
  suspend fun saveDateViewType(dateViewType: DateViewType)
  fun saveFileBackupDirectory(filePath: String)
  fun getFileBackupDirectory(): String?
}