package com.ra.bkuang.core.data.source.local.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.ra.bkuang.common.util.DateViewType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserSettingPrefImpl @Inject constructor(
  private val dataStore: DataStore<Preferences>
): UserSettingPref {

  companion object {
    private val KEY_DATE_VIEW_TYPE = stringPreferencesKey("key-date-view-type")
    private val KEY_ALARM_TRANSACTION = stringPreferencesKey("key-alarm-transaction")
    private val KEY_ACTIVE_ALARM = booleanPreferencesKey("key-active-alarm")
    private val KEY_BACKUP_FILE_NAME = stringPreferencesKey("key-backup-file-name")
  }

  override fun isActiveAlarmTransaction(): Flow<Boolean> {
    return dataStore.data.map {preferences ->
      preferences[KEY_ACTIVE_ALARM] ?: false
    }
  }

  override suspend fun setAlarmTransaction(state: Boolean) {
    dataStore.edit { preferences ->
      preferences[KEY_ACTIVE_ALARM] = state
    }
  }

  override fun getTextAlarmTransaction(): Flow<String> {
    return dataStore.data.map { preferences ->
     preferences[KEY_ALARM_TRANSACTION] ?: "00 : 00"
    }
  }

  override suspend fun setTextAlarmTransaction(text: String) {
    dataStore.edit { preferences ->
      preferences[KEY_ALARM_TRANSACTION] = text
    }
  }

  override fun getDateViewType(): Flow<String>  {
    return dataStore.data.map { preferences ->
      preferences[KEY_DATE_VIEW_TYPE] ?: DateViewType.DAILY.name
    }
  }

  override suspend fun saveDateViewType(dateViewType: DateViewType) {
    dataStore.edit { preferences ->
      preferences[KEY_DATE_VIEW_TYPE] = dateViewType.name
    }
  }

  override suspend fun saveFileBackupDirectory(filePath: String) {
    dataStore.edit { preferences ->
      preferences[KEY_BACKUP_FILE_NAME] = filePath
    }
  }

  override fun getFileBackupDirectory(): Flow<String> {
    return dataStore.data.map { preferences ->
      preferences[KEY_BACKUP_FILE_NAME] ?: ""
    }
  }
}