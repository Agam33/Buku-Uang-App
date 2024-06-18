package com.ra.bkuang.features.account.presentation.createaccount.viewmodel

import androidx.lifecycle.viewModelScope
import com.ra.bkuang.common.base.BaseViewModel
import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.account.domain.model.AkunModel
import com.ra.bkuang.features.account.domain.usecase.SaveAkunUseCase
import com.ra.bkuang.features.account.domain.usecase.UpdateAkunUseCase
import com.ra.bkuang.features.account.presentation.createaccount.CreateAccountUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateAccountViewModel @Inject constructor(
  private val saveAkunUseCase: SaveAkunUseCase,
  private val updateAkunUseCase: UpdateAkunUseCase,
): BaseViewModel<CreateAccountUiState>(CreateAccountUiState()) {

  fun createAccount(akun: AkunModel) {
    viewModelScope.launch {
      saveAkunUseCase(akun).collect { res ->
        when(res) {
          is Result.Success -> {
            _uiState.update { it.copy( isSuccessful = true) }
            delay(1000)
            _uiState.update { it.copy( isSuccessful = null) }
          }
          is Result.Error -> {
            _uiState.update { it.copy( isSuccessful = true) }
          }
        }
      }
    }
  }

  fun updateAccount(akun: AkunModel) {
    viewModelScope.launch {
      updateAkunUseCase.invoke(akun).collect { res ->
        when(res) {
          is Result.Success -> {
            _uiState.update { it.copy( isSuccessful = true) }
            delay(1000)
            _uiState.update { it.copy( isSuccessful = null) }
          }
          is Result.Error -> {
            _uiState.update { it.copy( isSuccessful = false) }
          }
        }
      }
    }
  }
}