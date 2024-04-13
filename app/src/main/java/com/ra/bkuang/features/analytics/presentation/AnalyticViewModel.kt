package com.ra.bkuang.features.analytics.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ra.bkuang.features.analytics.domain.model.AnalyticModel
import com.ra.bkuang.features.analytics.domain.usecase.DetailAnalytics
import com.ra.bkuang.features.analytics.domain.usecase.ShowAnalyticList
import com.ra.bkuang.common.base.BaseViewModel
import com.ra.bkuang.common.util.Resource
import com.ra.bkuang.features.transaction.domain.model.TransactionDetail
import com.ra.bkuang.features.transaction.presentation.TransactionType
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