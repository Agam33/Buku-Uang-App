package com.ra.bkuang.features.account.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.ra.bkuang.common.base.BaseViewModel
import com.ra.bkuang.common.util.Extension.toFormatRupiah
import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.account.domain.model.AkunModel
import com.ra.bkuang.features.account.domain.usecase.AkunOverallMoneyUseCase
import com.ra.bkuang.features.account.domain.usecase.DeleteAkunUseCase
import com.ra.bkuang.features.account.domain.usecase.FindAllAkunWithFlowUseCase
import com.ra.bkuang.features.account.presentation.AccountUiState
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.GetTotalPendapatanWithFlowUseCase
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.GetTotalPengeluaranWithFlowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
  private val deleteAkunUseCase: DeleteAkunUseCase,
  private val findAllAkun: FindAllAkunWithFlowUseCase,
  private val akunOverallMoneyUseCase: AkunOverallMoneyUseCase,
  private val getTotalPengeluaranWithFlowUseCase: GetTotalPengeluaranWithFlowUseCase,
  private val getTotalPendapatanWithFlowUseCase: GetTotalPendapatanWithFlowUseCase
): BaseViewModel() {

  private var _accountUiState = MutableStateFlow(AccountUiState())
  val accountUiState = _accountUiState.asStateFlow()

  fun getOverallMoney() {
    viewModelScope.launch {
      akunOverallMoneyUseCase().collect {
        val data = it ?: 0
        _accountUiState.update { state ->
          state.copy(
            totalMoney = data.toFormatRupiah()
          )
        }
      }
    }

    viewModelScope.launch {
      getTotalPendapatanWithFlowUseCase.invoke().collect {
        val data = it ?: 0
        _accountUiState.update { state ->
          state.copy(
            totalIncome =  data.toFormatRupiah()
          )
        }
      }
    }

    viewModelScope.launch {
      getTotalPengeluaranWithFlowUseCase.invoke().collect {
        val data = it ?: 0
        _accountUiState.update { state ->
          state.copy(
            totalExpense = data.toFormatRupiah()
          )
        }
      }
    }
  }

  fun getAllAccount() {
    viewModelScope.launch {
      findAllAkun().collect { list ->
        if (list.isEmpty()) {
          _accountUiState.update {
            it.copy(
              isEmptyAccount = true,
              accounts = emptyList()
            )
          }
        } else {
          _accountUiState.update {
            it.copy(
              accounts = list,
              isEmptyAccount = false
            )
          }
        }
      }
    }
  }

  fun deleteAccount(akun: AkunModel) {
    viewModelScope.launch {
      _accountUiState.update {
        it.copy(
          isSuccessfulDelete = null
        )
      }
      deleteAkunUseCase.invoke(akun).collect { res ->
        when(res) {
          is Result.Error -> {
            _accountUiState.update {
              it.copy(
                isSuccessfulDelete = false
              )
            }
          }
          is Result.Success -> {
            _accountUiState.update {
              it.copy(
                isSuccessfulDelete = true
              )
            }
            _accountUiState.update {
              it.copy(
                isSuccessfulDelete = null
              )
            }
          }
        }
      }
    }
  }
}