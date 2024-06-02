package com.ra.bkuang.features.backuprestore.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ra.bkuang.features.backuprestore.domain.usecase.CreateLocalBackupUseCase
import com.ra.bkuang.features.backuprestore.domain.usecase.GetLocalBackupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BackupRestoreViewModel @Inject constructor(
  private val createLocalBackupUseCase: CreateLocalBackupUseCase,
  private val getLocalBackupUseCase: GetLocalBackupUseCase
): ViewModel() {

  private var _backupRestoreUiState = MutableStateFlow(BackupRestoreUiState())
  val backupRestoreUiState = _backupRestoreUiState.asStateFlow()

   fun createLocalBackup(fileName: String, destDirectory: String) {
      viewModelScope.launch {
        _backupRestoreUiState.update {
          it.copy(
            isSuccessful = null
          )
        }

        val isSuccess =  createLocalBackupUseCase.invoke(fileName, destDirectory)

        if(isSuccess)  {
          _backupRestoreUiState.update {
            it.copy(
              isSuccessful = true
            )
          }

          _backupRestoreUiState.update {
            it.copy(
              isSuccessful = null
            )
          }
        } else {
          _backupRestoreUiState.update {
            it.copy(
              isSuccessful = false
            )
          }
        }
      }
   }


  fun getLocalBackup(uri: Uri, directoryDb: String) {
    viewModelScope.launch {
      _backupRestoreUiState.update {
        it.copy(
          isSuccessful = null
        )
      }

      val isSuccess = getLocalBackupUseCase.invoke(uri, directoryDb)

      if (isSuccess) {
        _backupRestoreUiState.update {
          it.copy(
            isSuccessful = true
          )
        }
        _backupRestoreUiState.update {
          it.copy(
            isSuccessful = null
          )
        }
      } else {
        _backupRestoreUiState.update {
          it.copy(
            isSuccessful = false
          )
        }
      }
    }
  }
}