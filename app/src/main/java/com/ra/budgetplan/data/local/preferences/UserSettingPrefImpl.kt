package com.ra.budgetplan.data.local.preferences

import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.ra.budgetplan.presentation.ui.transaction.adapter.DateViewType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserSettingPrefImpl @Inject constructor(
 private val dataStore: DataStore<Preferences>,
 private val sharedPreferences: SharedPreferences
): UserSettingPref {

  private val KEY_DATE_VIEW_TYPE = stringPreferencesKey("key-date-view-type")

  companion object {
    private const val KEY_BACKUP_FILE_PATH = "backup-file-path"
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

  override fun saveFileBackupDirectory(filePath: String) {
    sharedPreferences.edit()
      .putString(KEY_BACKUP_FILE_PATH, filePath)
      .apply()
  }

  override fun getFileBackupDirectory(): String? {
    return sharedPreferences.getString(KEY_BACKUP_FILE_PATH, "")
  }
}