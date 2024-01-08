package com.ra.bkuang.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ra.bkuang.domain.model.AnalyticModel
import com.ra.bkuang.domain.usecase.analisis.DetailAnalytics
import com.ra.bkuang.domain.usecase.analisis.ShowAnalyticList
import com.ra.bkuang.domain.util.Resource
import com.ra.bkuang.presentation.ui.features.transaction.TransactionDetail
import com.ra.bkuang.presentation.ui.features.transaction.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class AnalyticViewModel @Inject constructor(
  private val showAnalyticList: ShowAnalyticList,
  private val detailAnalytics: DetailAnalytics
): BaseViewModel() {

  private var _analyticList = MutableLiveData<Resource<List<AnalyticModel>>>()
  val analyticList: LiveData<Resource<List<AnalyticModel>>> get() = _analyticList

  private var _detailTransactions = MutableLiveData<Resource<List<TransactionDetail>>>()
  val detailTransactions: LiveData<Resource<List<TransactionDetail>>> get() = _detailTransactions

  private var _rvAnalyticState = MutableLiveData<Boolean>()
  val rvAnalyticState: LiveData<Boolean> = _rvAnalyticState

  fun getAnalyticList(
    transactionType: TransactionType,
    fromDate: LocalDateTime,
    toDate: LocalDateTime
  ) {
    viewModelScope.launch {
      val data = showAnalyticList.invoke(transactionType, fromDate, toDate)
      _analyticList.postValue(data)
    }
  }

  fun getDetailAnalytic(
    transactionType: TransactionType,
    fromDate: LocalDateTime,
    toDate: LocalDateTime
  ) {
    viewModelScope.launch {
      val data = detailAnalytics.invoke(transactionType, fromDate, toDate)
      _detailTransactions.postValue(data)
    }
  }

  fun setRvAnalyticState(state: Boolean) {
    _rvAnalyticState.postValue(state)
  }
}