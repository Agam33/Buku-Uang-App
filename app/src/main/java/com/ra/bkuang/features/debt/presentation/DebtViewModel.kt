package com.ra.bkuang.features.debt.presentation

import androidx.lifecycle.viewModelScope
import com.ra.bkuang.common.base.BaseViewModel
import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.debt.domain.model.HutangModel
import com.ra.bkuang.features.debt.domain.usecase.CreateHutangUseCase
import com.ra.bkuang.features.debt.domain.usecase.ShowAllHutangUseCase
import com.ra.bkuang.features.debt.domain.usecase.UpdateHutangUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DebtViewModel @Inject constructor(
  private val createHutangUseCase: CreateHutangUseCase,
  private val updateHutangUseCase: UpdateHutangUseCase,
  private val showAllHutangUseCase: ShowAllHutangUseCase,
): BaseViewModel() {

  private var _debtFragmentUiState = MutableStateFlow(DebtFragmentUiState())
  val debtFragmentUiState: StateFlow<DebtFragmentUiState> = _debtFragmentUiState

  fun getHutangList() {
    viewModelScope.launch {
      showAllHutangUseCase.invoke().collect { res ->
         when(res) {
           is Result.Success -> {
             _debtFragmentUiState.update { it.copy(debtList = res.data ?: emptyList()) }
           }
           is Result.Error -> {
             _debtFragmentUiState.update { it.copy(debtList = emptyList()) }
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
            _debtFragmentUiState.update { it.copy(isSuccessfulCreate = true) }
          }
          is Result.Error -> {
            _debtFragmentUiState.update { it.copy(isSuccessfulCreate = true) }
          }
        }
        _debtFragmentUiState.update { it.copy(isSuccessfulCreate = null) }
      }
    }
  }

  fun updateHutang(hutangModel: HutangModel) {
    viewModelScope.launch {
      updateHutangUseCase(hutangModel).collect { res ->
        when (res) {
          is Result.Success -> {
            _debtFragmentUiState.update { it.copy(isSuccessfulUpdate = true) }
          }
          is Result.Error -> {
            _debtFragmentUiState.update { it.copy(isSuccessfulUpdate = false) }
          }
        }

        _debtFragmentUiState.update { it.copy(isSuccessfulUpdate = null) }
      }
    }
  }
}