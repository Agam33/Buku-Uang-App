package com.ra.bkuang.features.debt.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.debt.domain.model.HutangModel
import com.ra.bkuang.features.debt.domain.usecase.CreateHutangUseCase
import com.ra.bkuang.features.debt.domain.usecase.FindHutangByIdUseCase
import com.ra.bkuang.features.debt.domain.usecase.ShowAllHutangUseCase
import com.ra.bkuang.features.debt.domain.usecase.UpdateHutangUseCase
import com.ra.bkuang.common.base.BaseViewModel
import com.ra.bkuang.common.util.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DebtViewModel @Inject constructor(
  private val createHutangUseCase: CreateHutangUseCase,
  private val updateHutangUseCase: UpdateHutangUseCase,
  private val showAllHutangUseCase: ShowAllHutangUseCase,
  private val findHutangByIdUseCase: FindHutangByIdUseCase,
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
      val data = showAllHutangUseCase.invoke()
      _hutangList.postValue(data)
    }
  }

  fun getHutangById(id: String) {
    viewModelScope.launch {
      val data = findHutangByIdUseCase.invoke(id)
      _hutangModel.postValue(data)
    }
  }

  suspend fun createHutang(hutangModel: HutangModel) = withContext(ioDispatcher) {
    return@withContext createHutangUseCase.invoke(hutangModel)
  }

  suspend fun updateHutang(hutangModel: HutangModel) = withContext(ioDispatcher) {
      return@withContext updateHutangUseCase.invoke(hutangModel)
  }
}