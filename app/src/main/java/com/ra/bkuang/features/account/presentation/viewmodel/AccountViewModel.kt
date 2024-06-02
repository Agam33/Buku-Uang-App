package com.ra.bkuang.features.account.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.ra.bkuang.common.base.BaseViewModel
import com.ra.bkuang.common.util.Extension.toFormatRupiah
import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.account.domain.model.AkunModel
import com.ra.bkuang.features.account.domain.usecase.AkunOverallMoney
import com.ra.bkuang.features.account.domain.usecase.DeleteAkun
import com.ra.bkuang.features.account.domain.usecase.FindAllAkunWithFlow
import com.ra.bkuang.features.account.presentation.AccountUiState
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.GetTotalPendapatanWithFlow
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.GetTotalPengeluaranWithFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
  private val deleteAkun: DeleteAkun,
  private val findAllAkun: FindAllAkunWithFlow,
  private val akunOverallMoney: AkunOverallMoney,
  private val getTotalPengeluaranWithFlow: GetTotalPengeluaranWithFlow,
  private val getTotalPendapatanWithFlow: GetTotalPendapatanWithFlow
): BaseViewModel() {

  private var _accountUiState = MutableStateFlow(AccountUiState())
  val accountUiState = _accountUiState.asStateFlow()

  fun getOverallMoney() {
    viewModelScope.launch {
      akunOverallMoney().collect {
        val data = it ?: 0
        _accountUiState.update { state ->
          state.copy(
            totalMoney = data.toFormatRupiah()
          )
        }
      }
    }

    viewModelScope.launch {
      getTotalPendapatanWithFlow.invoke().collect {
        val data = it ?: 0
        _accountUiState.update { state ->
          state.copy(
            totalIncome =  data.toFormatRupiah()
          )
        }
      }
    }

    viewModelScope.launch {
      getTotalPengeluaranWithFlow.invoke().collect {
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
      deleteAkun.invoke(akun).collect { res ->
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