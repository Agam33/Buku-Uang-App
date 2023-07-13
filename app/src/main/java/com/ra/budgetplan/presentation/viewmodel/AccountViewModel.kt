package com.ra.budgetplan.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ra.budgetplan.domain.model.AkunModel
import com.ra.budgetplan.domain.usecase.akun.AkunOverallMoney
import com.ra.budgetplan.domain.usecase.akun.DeleteAkun
import com.ra.budgetplan.domain.usecase.akun.FindAllAkun
import com.ra.budgetplan.domain.usecase.akun.SaveAkun
import com.ra.budgetplan.domain.usecase.akun.UpdateAkun
import com.ra.budgetplan.domain.usecase.transaksi.pendapatan.GetTotalPendapatan
import com.ra.budgetplan.domain.usecase.transaksi.pengeluaran.GetTotalPengeluaran
import com.ra.budgetplan.util.Resource
import com.ra.budgetplan.util.toFormatRupiah
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
  private val getTotalPengeluaran: GetTotalPengeluaran,
  private val getTotalPendapatan: GetTotalPendapatan
): BaseViewModel() {

  private var _rvAccountState = MutableLiveData<Boolean>()
  val rvAccountState: LiveData<Boolean> = _rvAccountState

  private var _emptyMessageState = MutableLiveData<Boolean>()
  val emptyMessageState: LiveData<Boolean> = _emptyMessageState

  private var _accounts = MutableLiveData<List<AkunModel>>()
  val accounts: LiveData<List<AkunModel>> = _accounts

  private var _totalExpense = MutableLiveData<String>()
  val totalExpense: LiveData<String> = _totalExpense

  private var _totalIncome = MutableLiveData<String>()
  val totalIncome: LiveData<String> = _totalIncome

  private var _totalAccountMoney = MutableLiveData<String>()
  val totalAccountMoney: LiveData<String> = _totalAccountMoney

  init {
    getAllAccount()
    getOverallMoney()
  }

  private fun getOverallMoney() {
    viewModelScope.launch {
      akunOverallMoney.invoke().collect {
        _totalAccountMoney.postValue(it.toFormatRupiah())
      }
    }

    viewModelScope.launch {
      getTotalPendapatan.invoke().collect {
        _totalIncome.postValue(it.toFormatRupiah())
      }
    }

    viewModelScope.launch {
      getTotalPengeluaran.invoke().collect {
        _totalExpense.postValue(it.toFormatRupiah())
      }
    }
  }

  private fun getAllAccount() {
    viewModelScope.launch {
      findAllAkun.invoke().collect { resource ->
        when (resource) {
          is Resource.Empty -> {
            _rvAccountState.postValue(true)
            _emptyMessageState.postValue(false)
          }

          is Resource.Success -> {
            _emptyMessageState.postValue(true)
            _rvAccountState.postValue(false)
            _accounts.postValue(resource.data ?: mutableListOf())
          }

          else -> {}
        }
      }
    }
  }

  fun deleteAccount(akun: AkunModel) = viewModelScope.launch {
    deleteAkun.invoke(akun)
  }

  fun updateAccount(akun: AkunModel) = viewModelScope.launch {
    updateAkun.invoke(akun)
  }

  fun createAccount(akun: AkunModel) = viewModelScope.launch {
    saveAkun.invoke(akun)
  }
}