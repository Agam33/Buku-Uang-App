package com.ra.bkuang.presentation.ui.debt

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.domain.model.HutangModel
import com.ra.bkuang.domain.usecase.hutang.CancelAlarmDebt
import com.ra.bkuang.domain.usecase.hutang.CreateHutang
import com.ra.bkuang.domain.usecase.hutang.DeleteHutang
import com.ra.bkuang.domain.usecase.hutang.FindHutangById
import com.ra.bkuang.domain.usecase.hutang.SetAlarmDebt
import com.ra.bkuang.domain.usecase.hutang.ShowAllHutang
import com.ra.bkuang.domain.usecase.hutang.UpdateHutang
import com.ra.bkuang.presentation.base.BaseViewModel
import com.ra.bkuang.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.UUID
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

  private var _hutangList = MutableLiveData<Resource<List<HutangModel>>>()
  val hutangList: LiveData<Resource<List<HutangModel>>> get() = _hutangList

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