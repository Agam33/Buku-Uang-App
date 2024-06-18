package com.ra.bkuang.features.analytics.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.ra.bkuang.common.base.BaseViewModel
import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.analytics.domain.usecase.DetailAnalyticsUseCase
import com.ra.bkuang.features.analytics.domain.usecase.ShowAnalyticListUseCase
import com.ra.bkuang.features.analytics.presentation.AnalyticUiState
import com.ra.bkuang.features.transaction.presentation.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class AnalyticViewModel @Inject constructor(
  private val showAnalyticListUseCase: ShowAnalyticListUseCase,
  private val detailAnalyticsUseCase: DetailAnalyticsUseCase
): BaseViewModel<AnalyticUiState>(AnalyticUiState()) {

  fun getAnalyticList(
    transactionType: TransactionType,
    fromDate: LocalDateTime,
    toDate: LocalDateTime
  ) {
    viewModelScope.launch {
      showAnalyticListUseCase(transactionType, fromDate, toDate).collect { res ->
        when(res) {
          is Result.Success -> {
            _uiState.update {
              it.copy(
                analytics = res.data ?: mutableListOf(),
              )
            }
          }
          is Result.Error -> {
            _uiState.update {
              it.copy(
                analytics = mutableListOf(),
              )
            }
          }
        }
      }
    }
  }

  fun getDetailAnalytic(
    transactionType: TransactionType,
    fromDate: LocalDateTime,
    toDate: LocalDateTime
  ) {
    viewModelScope.launch {
      detailAnalyticsUseCase.invoke(transactionType, fromDate, toDate).collect { res ->
        when(res) {
          is Result.Error -> {
            _uiState.update {
              it.copy(
                detailAnalyticList = emptyList()
              )
            }
          }
          is Result.Success -> {
            _uiState.update {
              it.copy(
                detailAnalyticList = res.data ?: emptyList()
              )
            }
          }
        }
      }
    }
  }
}