package com.ra.bkuang.features.transaction.presentation.tab.income.viewmodel

import androidx.lifecycle.viewModelScope
import com.ra.bkuang.common.base.BaseViewModel
import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.DeletePendapatanByIdUseCase
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.GetListDetailPendapatanByDateUseCase
import com.ra.bkuang.features.transaction.presentation.tab.income.uistate.IncomeFragmentUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class IncomeFragmentViewModel @Inject constructor(
  private val getListDetailPendapatanByDateUseCase: GetListDetailPendapatanByDateUseCase,
  private val deletePendapatanByIdUseCase: DeletePendapatanByIdUseCase,
): BaseViewModel() {

  private var _uiState = MutableStateFlow(IncomeFragmentUiState())
  val uiState = _uiState.asStateFlow()

  fun getIncomeByDate(fromDate: LocalDateTime, toDate: LocalDateTime)  {
    viewModelScope.launch {
      getListDetailPendapatanByDateUseCase(fromDate, toDate).collect { res ->
        when(res) {
          is Result.Error -> {
            _uiState.update {
              it.copy(
                incomeList = emptyList()
              )
            }
          }
          is Result.Success -> {
            _uiState.update {
              it.copy(
                incomeList = res.data ?: mutableListOf()
              )
            }
          }
        }
      }
    }
  }

  fun deleteIncomeById(uuid: UUID) {
    viewModelScope.launch {
      deletePendapatanByIdUseCase.invoke(uuid)
    }
  }
}