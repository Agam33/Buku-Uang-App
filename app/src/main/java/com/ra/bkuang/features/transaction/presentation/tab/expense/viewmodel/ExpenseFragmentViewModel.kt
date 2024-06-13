package com.ra.bkuang.features.transaction.presentation.tab.expense.viewmodel

import androidx.lifecycle.viewModelScope
import com.ra.bkuang.common.base.BaseViewModel
import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.DeletePengeluaranByIdUseCase
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.GetListDetailPengeluaranByDateUseCase
import com.ra.bkuang.features.transaction.presentation.tab.expense.uistate.ExpenseFragmentUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ExpenseFragmentViewModel @Inject constructor(
  private val getListDetailPengeluaranByDateUseCase: GetListDetailPengeluaranByDateUseCase,
  private val deletePengeluaranByIdUseCase: DeletePengeluaranByIdUseCase,
): BaseViewModel() {

  private var _uiState = MutableStateFlow(ExpenseFragmentUiState())
  val uiState = _uiState.asStateFlow()

  fun getExpenseByDate(fromDate: LocalDateTime, toDate: LocalDateTime) {
    viewModelScope.launch {
      getListDetailPengeluaranByDateUseCase(fromDate, toDate).collect { res ->
        when(res) {
          is Result.Success -> {
            _uiState.update {
              it.copy(
                expenseList = res.data ?: mutableListOf()
              )
            }
          }
          is Result.Error -> {
            _uiState.update {
              it.copy(
                expenseList = emptyList()
              )
            }
          }
        }
      }
    }
  }

  fun deleteExpenseById(uuid: UUID) {
    viewModelScope.launch {
      deletePengeluaranByIdUseCase(uuid).collect { res ->
        when(res) {
          is Result.Success -> {
            _uiState.update {
              it.copy(
                isSuccessful = res.data
              )
            }
          }
          is Result.Error -> {
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
  }
}