package com.ra.bkuang.features.backuprestore.presentation.viewmodel

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.ra.bkuang.common.base.BaseViewModel
import com.ra.bkuang.core.preferences.UserSettingPref
import com.ra.bkuang.features.backuprestore.domain.usecase.CreateLocalBackupUseCase
import com.ra.bkuang.features.backuprestore.domain.usecase.GetLocalBackupUseCase
import com.ra.bkuang.features.backuprestore.presentation.BackupRestoreUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BackupRestoreViewModel @Inject constructor(
  private val createLocalBackupUseCase: CreateLocalBackupUseCase,
  private val getLocalBackupUseCase: GetLocalBackupUseCase,
  private val userSettingPref: UserSettingPref
): BaseViewModel<BackupRestoreUiState>(BackupRestoreUiState()) {

  fun saveBackupFile(dir: String) {
    viewModelScope.launch {
      userSettingPref.saveFileBackupDirectory(dir)
    }
  }

  fun checkBackupFile() {
    viewModelScope.launch {
      val dir = userSettingPref.getFileBackupDirectory().first()

      if(dir.isBlank() || dir.isEmpty()) {
        _uiState.update {
          it.copy(
            isDirExists = false
          )
        }
      } else {
        _uiState.update {
          it.copy(
            isDirExists = true
          )
        }
      }
    }

    _uiState.update {
      it.copy(
        isDirExists = null
      )
    }
  }

   fun createLocalBackup(fileName: String) {
      viewModelScope.launch {
        val dir = userSettingPref.getFileBackupDirectory().first()

        val isSuccess =  createLocalBackupUseCase.invoke(fileName, dir)

        if(isSuccess)  {
          _uiState.update {
            it.copy(
              isSuccessful = true
            )
          }
        } else {
          _uiState.update {
            it.copy(
              isSuccessful = false
            )
          }
        }
      }

     _uiState.update {
       it.copy(
         isSuccessful = null
       )
     }
   }

  fun getLocalBackup(uri: Uri, directoryDb: String) {
    viewModelScope.launch {
      val isSuccess = getLocalBackupUseCase.invoke(uri, directoryDb)

      if (isSuccess) {
        _uiState.update {
          it.copy(
            isSuccessful = true
          )
        }
      } else {
        _uiState.update {
          it.copy(
            isSuccessful = false
          )
        }
      }
    }

    _uiState.update {
      it.copy(
        isSuccessful = null
      )
    }
  }
}