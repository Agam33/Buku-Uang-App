package com.ra.bkuang.features.transaction.presentation

import androidx.lifecycle.viewModelScope
import com.ra.bkuang.common.base.BaseViewModel
import com.ra.bkuang.common.util.Extension.toFormatRupiah
import com.ra.bkuang.core.preferences.UserSettingPref
import com.ra.bkuang.features.transaction.domain.usecase.GetTotalTransactionByDateUseCase
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.GetTotalPendapatanByDateWithFlowUseCase
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.GetTotalPengeluaranByDateWithFlowUseCase
import com.ra.bkuang.features.transaction.utils.dateByViewType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
  private val userSettingPref: UserSettingPref,
  private val getTotalPendapatanByDateWithFlowUseCase: GetTotalPendapatanByDateWithFlowUseCase,
  private val getTotalPengeluaranByDateWithFlowUseCase: GetTotalPengeluaranByDateWithFlowUseCase,
  private val getTotalTransactionByDateUseCase: GetTotalTransactionByDateUseCase,
): BaseViewModel() {

  private val _uiState = MutableStateFlow(TransactionUiState())
  val uiState = _uiState.asStateFlow()

  fun initTransaction(localDate: LocalDate) {
    viewModelScope.launch {
      val viewType = userSettingPref.getDateViewType().first()
      setCurrentDate(localDate.dateByViewType(viewType))
    }
  }

  fun initDateView() {
    viewModelScope.launch {
      userSettingPref.getDateViewType().collect { viewType ->
        _uiState.update {
          it.copy(
            dateViewType = viewType,
          )
        }
      }
    }
  }

  fun setCurrentDate(localDate: Pair<LocalDateTime, LocalDateTime>) {
    _uiState.update {
      it.copy(
        currDate = localDate
      )
    }
  }

  fun getTotalIncomeByDate(fromDate: LocalDateTime, toDate: LocalDateTime) {
    viewModelScope.launch {
      getTotalPendapatanByDateWithFlowUseCase(fromDate, toDate).collect { income ->
        _uiState.update {
          it.copy(
            incomeText = income.toFormatRupiah()
          )
        }
      }
    }
  }

  fun getTotalExpenseByDate(fromDate: LocalDateTime, toDate: LocalDateTime) {
    viewModelScope.launch {
      getTotalPengeluaranByDateWithFlowUseCase(fromDate, toDate).collect { expense ->
        _uiState.update {
          it.copy(
            expenseText = expense.toFormatRupiah()
          )
        }
      }
    }
  }

  fun getTotalByDate(fromDate: LocalDateTime, toDate: LocalDateTime) {
    viewModelScope.launch {
      getTotalTransactionByDateUseCase(fromDate, toDate).collect { total ->
        _uiState.update {
          it.copy(
            totalTransactionText = total.toFormatRupiah()
          )
        }
      }
    }
  }
}