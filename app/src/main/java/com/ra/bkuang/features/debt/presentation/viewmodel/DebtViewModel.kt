package com.ra.bkuang.features.debt.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.ra.bkuang.common.base.BaseViewModel
import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.debt.data.model.HutangModel
import com.ra.bkuang.features.debt.domain.usecase.CreateHutangUseCase
import com.ra.bkuang.features.debt.domain.usecase.ShowAllHutangUseCase
import com.ra.bkuang.features.debt.domain.usecase.UpdateHutangUseCase
import com.ra.bkuang.features.debt.presentation.DebtFragmentUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DebtViewModel @Inject constructor(
  private val createHutangUseCase: CreateHutangUseCase,
  private val updateHutangUseCase: UpdateHutangUseCase,
  private val showAllHutangUseCase: ShowAllHutangUseCase,
): BaseViewModel<DebtFragmentUiState>(DebtFragmentUiState()) {

  fun getHutangList() {
    viewModelScope.launch {
      showAllHutangUseCase.invoke().collect { res ->
         when(res) {
           is Result.Success -> {
             _uiState.update { it.copy(debtList = res.data ?: emptyList()) }
           }
           is Result.Error -> {
             _uiState.update { it.copy(debtList = emptyList()) }
           }
         }
      }
    }
  }

  fun createHutang(hutangModel: HutangModel) {
    viewModelScope.launch {
      createHutangUseCase.invoke(hutangModel).collect { res ->
        when (res) {
          is Result.Success -> {
            _uiState.update { it.copy(isSuccessfulCreate = true) }
          }
          is Result.Error -> {
            _uiState.update { it.copy(isSuccessfulCreate = true) }
          }
        }
        _uiState.update { it.copy(isSuccessfulCreate = null) }
      }
    }
  }

  fun updateHutang(hutangModel: HutangModel) {
    viewModelScope.launch {
      updateHutangUseCase(hutangModel).collect { res ->
        when (res) {
          is Result.Success -> {
            _uiState.update { it.copy(isSuccessfulUpdate = true) }
          }
          is Result.Error -> {
            _uiState.update { it.copy(isSuccessfulUpdate = false) }
          }
        }

        _uiState.update { it.copy(isSuccessfulUpdate = null) }
      }
    }
  }
}