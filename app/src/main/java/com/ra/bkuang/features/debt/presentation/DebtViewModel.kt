package com.ra.bkuang.features.debt.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.debt.domain.model.HutangModel
import com.ra.bkuang.features.debt.domain.usecase.CreateHutang
import com.ra.bkuang.features.debt.domain.usecase.FindHutangById
import com.ra.bkuang.features.debt.domain.usecase.ShowAllHutang
import com.ra.bkuang.features.debt.domain.usecase.UpdateHutang
import com.ra.bkuang.common.base.BaseViewModel
import com.ra.bkuang.common.util.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DebtViewModel @Inject constructor(
  private val createHutang: CreateHutang,
  private val updateHutang: UpdateHutang,
  private val showAllHutang: ShowAllHutang,
  private val findHutangById: FindHutangById,
  @IoDispatcherQualifier private val ioDispatcher: CoroutineDispatcher
): BaseViewModel() {

  private var _rvDebtListState = MutableLiveData<Boolean>()
  val rvDebtListState: LiveData<Boolean> = _rvDebtListState

  private var _emptyListState = MutableLiveData<Boolean>()
  val emptyListState: LiveData<Boolean> = _emptyListState

  private var _hutangList = MutableLiveData<ResultState<List<HutangModel>>>()
  val hutangList: LiveData<ResultState<List<HutangModel>>> get() = _hutangList

  private var _hutangModel = MutableLiveData<HutangModel>()
  val hutangModel: LiveData<HutangModel> get() = _hutangModel

  fun setState(rvState: Boolean, emptyState: Boolean) {
    _rvDebtListState.postValue(rvState)
    _emptyListState.postValue(emptyState)
  }

  fun getHutangList() {
    viewModelScope.launch {
      val data = showAllHutang.invoke()
      _hutangList.postValue(data)
    }
  }

  fun getHutangById(id: String) {
    viewModelScope.launch {
      val data = findHutangById.invoke(id)
      _hutangModel.postValue(data)
    }
  }

  suspend fun createHutang(hutangModel: HutangModel) = withContext(ioDispatcher) {
    return@withContext createHutang.invoke(hutangModel)
  }

  suspend fun updateHutang(hutangModel: HutangModel) = withContext(ioDispatcher) {
      return@withContext updateHutang.invoke(hutangModel)
  }
}