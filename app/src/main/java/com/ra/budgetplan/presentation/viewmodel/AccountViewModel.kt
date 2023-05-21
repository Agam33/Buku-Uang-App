package com.ra.budgetplan.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ra.budgetplan.domain.model.AkunModel
import com.ra.budgetplan.domain.usecase.akun.DeleteAkun
import com.ra.budgetplan.domain.usecase.akun.FindAllAkun
import com.ra.budgetplan.domain.usecase.akun.SaveAkun
import com.ra.budgetplan.domain.usecase.akun.UpdateAkun
import com.ra.budgetplan.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
  private val saveAkun: SaveAkun,
  private val deleteAkun: DeleteAkun,
  private val updateAkun: UpdateAkun,
  private val findAllAkun: FindAllAkun
): ViewModel() {

  private var _rvAccountState = MutableLiveData<Boolean>()
  val rvAccountState: LiveData<Boolean> = _rvAccountState

  private var _emptyMessageState = MutableLiveData<Boolean>()
  val emptyMessageState: LiveData<Boolean> = _emptyMessageState

  private var _accounts = MutableLiveData<List<AkunModel>>()
  val accounts: LiveData<List<AkunModel>> = _accounts

  fun getAllAccount() {
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