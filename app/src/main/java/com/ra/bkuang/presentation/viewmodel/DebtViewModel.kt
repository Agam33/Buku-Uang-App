package com.ra.bkuang.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ra.bkuang.domain.model.HutangModel
import com.ra.bkuang.domain.usecase.hutang.CreateHutang
import com.ra.bkuang.domain.usecase.hutang.DeleteHutang
import com.ra.bkuang.domain.usecase.hutang.FindHutangById
import com.ra.bkuang.domain.usecase.hutang.ShowAllHutang
import com.ra.bkuang.domain.usecase.hutang.UpdateHutang
import com.ra.bkuang.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class DebtViewModel @Inject constructor(
  private val createHutang: CreateHutang,
  private val updateHutang: UpdateHutang,
  private val showAllHutang: ShowAllHutang,
  private val deleteHutang: DeleteHutang,
  private val findHutangById: FindHutangById
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

  fun getHutangById(id: UUID) {
    viewModelScope.launch {
      val data = findHutangById.invoke(id)
      _hutangModel.postValue(data)
    }
  }

  fun createHutang(hutangModel: HutangModel) =
    createHutang.invoke(hutangModel)

  suspend fun deleteHutang(hutang: HutangModel) =
    deleteHutang.invoke(hutang)

  fun updateHutang(hutangModel: HutangModel) =
    updateHutang.invoke(hutangModel)

}