package com.ra.bkuang.features.transaction.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.ra.bkuang.common.base.BaseViewModel
import com.ra.bkuang.common.util.Constants.DAILY_DATE_FORMAT
import com.ra.bkuang.common.util.Constants.LOCALE_ID
import com.ra.bkuang.common.util.Constants.MONTHLY_DATE_FORMAT
import com.ra.bkuang.common.util.DateViewType
import com.ra.bkuang.common.util.Extension.toFormatRupiah
import com.ra.bkuang.common.util.Extension.toStringFormat
import com.ra.bkuang.common.util.Result
import com.ra.bkuang.common.util.getDateViewType
import com.ra.bkuang.core.preferences.UserSettingPref
import com.ra.bkuang.features.transaction.domain.usecase.GetTotalTransactionByDateUseCase
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.DeletePendapatanByIdUseCase
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.GetListDetailPendapatanByDateUseCase
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.GetTotalPendapatanByDateWithFlowUseCase
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.DeletePengeluaranByIdUseCase
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.GetListDetailPengeluaranByDateUseCase
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.GetTotalPengeluaranByDateWithFlowUseCase
import com.ra.bkuang.features.transaction.domain.usecase.transfer.DeleteTransferByIdUseCase
import com.ra.bkuang.features.transaction.domain.usecase.transfer.GetTransferByDateUseCase
import com.ra.bkuang.features.transaction.presentation.TransactionUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
  private val userSettingPref: UserSettingPref,
  private val getTotalPendapatanByDateWithFlowUseCase: GetTotalPendapatanByDateWithFlowUseCase,
  private val getTotalPengeluaranByDateWithFlowUseCase: GetTotalPengeluaranByDateWithFlowUseCase,
  private val getTotalTransactionByDateUseCase: GetTotalTransactionByDateUseCase,

  private val getListDetailPengeluaranByDateUseCase: GetListDetailPengeluaranByDateUseCase,
  private val deletePengeluaranByIdUseCase: DeletePengeluaranByIdUseCase,

  private val getListDetailPendapatanByDateUseCase: GetListDetailPendapatanByDateUseCase,
  private val deletePendapatanByIdUseCase: DeletePendapatanByIdUseCase,

  private val getTransferByDateUseCase: GetTransferByDateUseCase,
  private val deleteTransferByIdUseCase: DeleteTransferByIdUseCase,
): BaseViewModel<TransactionUiState>(TransactionUiState()) {

  fun initTransaction() {
    viewModelScope.launch {
      val viewType = userSettingPref.getDateViewType().first()

      _uiState.update {
        it.copy(
          transactionCurrDate = LocalDate.now()
        )
      }

      _uiState.update {
        it.copy(
          dateViewType = viewType,
          currDate = it.dateByViewType(
            it.transactionCurrDate,
            viewType
          ),
          currDateText = it.transactionCurrDate.toStringFormat(
            when(getDateViewType(viewType)) {
              DateViewType.MONTHLY -> MONTHLY_DATE_FORMAT
              DateViewType.DAILY -> DAILY_DATE_FORMAT
            }, LOCALE_ID
          )
        )
      }
    }
  }

  fun changeViewDate() {
    viewModelScope.launch {
      val viewType = userSettingPref.getDateViewType().first()

      _uiState.update {
        it.copy(
          dateViewType = viewType,
          currDate = it.dateByViewType(
            it.transactionCurrDate,
            viewType
          ),
          currDateText = it.transactionCurrDate.toStringFormat(
            when(getDateViewType(viewType)) {
              DateViewType.MONTHLY -> MONTHLY_DATE_FORMAT
              DateViewType.DAILY -> DAILY_DATE_FORMAT
            }, LOCALE_ID
          )
        )
      }
    }
  }

  fun prevDate() {
    viewModelScope.launch {
      val viewType = userSettingPref.getDateViewType().first()
      when (getDateViewType(viewType)) {
        DateViewType.MONTHLY -> {
          _uiState.update {
            it.copy(
              transactionCurrDate = it.transactionCurrDate.minusMonths(1),
            )
          }

          _uiState.update {
            it.copy(
              currDate = it.dateByViewType(
                it.transactionCurrDate,
                viewType
              ),
              currDateText = it.transactionCurrDate.toStringFormat(MONTHLY_DATE_FORMAT, LOCALE_ID),
            )
          }
        }
        else -> {
          _uiState.update {
            it.copy(
              transactionCurrDate = it.transactionCurrDate.minusDays(1),
            )
          }

          _uiState.update {
            it.copy(
              currDate = it.dateByViewType(
                it.transactionCurrDate,
                viewType
              ),
              currDateText = it.transactionCurrDate.toStringFormat(DAILY_DATE_FORMAT, LOCALE_ID),
            )
          }
        }
      }
    }
  }

  fun nextDate() {
    viewModelScope.launch {
      val viewType = userSettingPref.getDateViewType().first()
      when (getDateViewType(viewType)) {
        DateViewType.MONTHLY -> {
          _uiState.update {
            it.copy(transactionCurrDate = it.transactionCurrDate.plusMonths(1),)
          }

          _uiState.update {
            it.copy(
              currDate = it.dateByViewType(
                it.transactionCurrDate,
                viewType
              ),
              currDateText = it.transactionCurrDate.toStringFormat(MONTHLY_DATE_FORMAT, LOCALE_ID),
            )
          }
        }
        else -> {
          _uiState.update {
            it.copy(
              transactionCurrDate = it.transactionCurrDate.plusDays(1),
            )
          }

          _uiState.update {
            it.copy(
              currDate = it.dateByViewType(
                it.transactionCurrDate,
                viewType
              ),
              currDateText = it.transactionCurrDate.toStringFormat(DAILY_DATE_FORMAT, LOCALE_ID),
            )
          }
        }
      }
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

  // Expense
  fun getExpenseByDate(fromDate: LocalDateTime, toDate: LocalDateTime) {
    viewModelScope.launch {
      getListDetailPengeluaranByDateUseCase(fromDate, toDate).collect { res ->
        when(res) {
          is Result.Success -> {
            _uiState.update {
              it.copy(
                expenseUiState = it.expenseUiState.copy(
                  expenseList = res.data ?: mutableListOf()
                )
              )
            }
          }
          is Result.Error -> {
            _uiState.update {
              it.copy(
                expenseUiState = it.expenseUiState.copy(
                  expenseList = emptyList()
                )
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
                expenseUiState = it.expenseUiState.copy(
                  isSuccessful = res.data
                )
              )
            }
          }
          is Result.Error -> {
            _uiState.update {
              it.copy(
                expenseUiState = it.expenseUiState.copy(
                  isSuccessful = false
                )
              )
            }
          }
        }

        _uiState.update {
          it.copy(
            expenseUiState = it.expenseUiState.copy(
              isSuccessful = null
            )
          )
        }
      }
    }
  }

  // Income
  fun getIncomeByDate(fromDate: LocalDateTime, toDate: LocalDateTime)  {
    viewModelScope.launch {
      getListDetailPendapatanByDateUseCase(fromDate, toDate).collect { res ->
        when(res) {
          is Result.Error -> {
            _uiState.update {
              it.copy(
                incomeUiState = it.incomeUiState.copy(
                  incomeList = emptyList()
                ),
              )
            }
          }
          is Result.Success -> {
            _uiState.update {
              it.copy(
                incomeUiState = it.incomeUiState.copy(
                  incomeList = res.data ?: mutableListOf()
                ),
              )
            }
          }
        }
      }
    }
  }

  fun deleteIncomeById(uuid: UUID) {
    viewModelScope.launch {
      deletePendapatanByIdUseCase.invoke(uuid).collect { res ->
        when (res) {
          is Result.Success -> {
            _uiState.update {
              it.copy(
                incomeUiState = it.incomeUiState.copy(
                  isSuccessful = res.data
                )
              )
            }
          }

          is Result.Error -> {
            _uiState.update {
              it.copy(
                incomeUiState = it.incomeUiState.copy(
                  isSuccessful = false
                )
              )
            }
          }
        }

        _uiState.update {
          it.copy(
            incomeUiState = it.incomeUiState.copy(
              isSuccessful = null
            )
          )
        }
      }
    }
  }

  // Transfer
  fun getTransferByDate(fromDate: LocalDateTime, toDate: LocalDateTime) {
    viewModelScope.launch {
      getTransferByDateUseCase.invoke(fromDate, toDate).collect { res ->
        when (res) {
          is Result.Error -> {
            _uiState.update {
              it.copy(
                transferFragmentUiState = it.transferFragmentUiState.copy(
                  transferList = emptyList()
                )
              )
            }
          }

          is Result.Success -> {
            _uiState.update {
              it.copy(
                transferFragmentUiState = it.transferFragmentUiState.copy(
                  transferList = res.data ?: mutableListOf()
                )
              )
            }
          }
        }
      }
    }
  }

  fun deleteTransferById(uuid: UUID) {
    viewModelScope.launch {
      deleteTransferByIdUseCase.invoke(uuid).collect { res ->
        when(res) {
          is Result.Success -> {
            _uiState.update {
              it.copy(
                transferFragmentUiState = it.transferFragmentUiState.copy(
                  isSuccessful = res.data
                )
              )
            }
          }
          is Result.Error -> {
            _uiState.update {
              it.copy(
                transferFragmentUiState = it.transferFragmentUiState.copy(
                  isSuccessful = false
                )
              )
            }
          }
        }

        _uiState.update {
          it.copy(
            transferFragmentUiState = it.transferFragmentUiState.copy(
              isSuccessful = null
            )
          )
        }
      }
    }
  }
}