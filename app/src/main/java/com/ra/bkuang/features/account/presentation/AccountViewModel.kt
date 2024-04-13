package com.ra.bkuang.features.account.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ra.bkuang.features.account.domain.model.AkunModel
import com.ra.bkuang.features.account.domain.usecase.AkunOverallMoney
import com.ra.bkuang.features.account.domain.usecase.DeleteAkun
import com.ra.bkuang.features.account.domain.usecase.FindAllAkun
import com.ra.bkuang.features.account.domain.usecase.SaveAkun
import com.ra.bkuang.features.account.domain.usecase.UpdateAkun
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.GetTotalPendapatanWithFlow
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.GetTotalPengeluaranWithFlow
import com.ra.bkuang.common.base.BaseViewModel
import com.ra.bkuang.common.util.Extension.toFormatRupiah
import com.ra.bkuang.common.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
  private val saveAkun: SaveAkun,
  private val deleteAkun: DeleteAkun,
  private val updateAkun: UpdateAkun,
  private val findAllAkun: FindAllAkun,
  private val akunOverallMoney: AkunOverallMoney,
  private val getTotalPengeluaranWithFlow: GetTotalPengeluaranWithFlow,
  private val getTotalPendapatanWithFlow: GetTotalPendapatanWithFlow
): BaseViewModel() {

  private var _rvAccountState = MutableLiveData<Boolean>()
  val rvAccountState: LiveData<Boolean> = _rvAccountState

  private var _emptyMessageState = MutableLiveData<Boolean>()
  val emptyMessageState: LiveData<Boolean> = _emptyMessageState

  private var _accounts = MutableLiveData<Resource<List<AkunModel>>>()
  val accounts: LiveData<Resource<List<AkunModel>>> = _accounts

  private var _totalExpense = MutableLiveData<String>()
  val totalExpense: LiveData<String> = _totalExpense

  private var _totalIncome = MutableLiveData<String>()
  val totalIncome: LiveData<String> = _totalIncome

  private var _totalAccountMoney = MutableLiveData<String>()
  val totalAccountMoney: LiveData<String> = _totalAccountMoney

  fun setRvState(state: Boolean) {
    _rvAccountState.postValue(state)
  }

  fun setEmptyLayoutState(state: Boolean) {
    _emptyMessageState.postValue(state)
  }

  fun getOverallMoney() {
    viewModelScope.launch {
      akunOverallMoney.invoke().collect {
        _totalAccountMoney.postValue(it.toFormatRupiah())
      }
    }

    viewModelScope.launch {
      getTotalPendapatanWithFlow.invoke().collect {
        _totalIncome.postValue(it.toFormatRupiah())
      }
    }

    viewModelScope.launch {
      getTotalPengeluaranWithFlow.invoke().collect {
        _totalExpense.postValue(it.toFormatRupiah())
      }
    }
  }

  fun getAllAccount() {
    viewModelScope.launch {
      val list = findAllAkun.invoke()
      _accounts.postValue(Resource.Loading())
      if(list.isEmpty()) {
        _accounts.postValue(Resource.Empty(""))
      } else {
        _accounts.postValue(Resource.Success(list))
      }
    }
  }

  suspend fun deleteAccount(akun: AkunModel) = deleteAkun.invoke(akun)

  fun updateAccount(akun: AkunModel) = viewModelScope.launch {
    updateAkun.invoke(akun)
  }

  fun createAccount(akun: AkunModel) = viewModelScope.launch {
    saveAkun.invoke(akun)
  }
}