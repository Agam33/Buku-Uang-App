package com.ra.budgetplan.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ra.budgetplan.domain.entity.TipeKategori
import com.ra.budgetplan.domain.model.AkunModel
import com.ra.budgetplan.domain.model.KategoriModel
import com.ra.budgetplan.domain.model.TransferModel
import com.ra.budgetplan.domain.usecase.akun.FindAllAkun
import com.ra.budgetplan.domain.usecase.akun.FindCategoryByType
import com.ra.budgetplan.domain.usecase.kategori.FindAllKategori
import com.ra.budgetplan.domain.usecase.transaksi.transfer.SaveTransfer
import com.ra.budgetplan.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
  private val saveTransfer: SaveTransfer,
  private val findAllAkun: FindAllAkun,
  private val findAllKategori: FindAllKategori,
  private val findKategoriByType: FindCategoryByType
): ViewModel() {

  private var _listCategory = MutableLiveData<List<KategoriModel>>()
  val listCategory: LiveData<List<KategoriModel>> get() = _listCategory

  private var _listAccount = MutableLiveData<List<AkunModel>>()
  val listAccount: LiveData<List<AkunModel>> get() = _listAccount

  private var _listCategoryByType = MutableLiveData<List<KategoriModel>>()
  val listCategoryByType: LiveData<List<KategoriModel>> get() = _listCategoryByType

  fun getAllAccount() = viewModelScope.launch {
    findAllAkun.invoke().collect {
      when(it) {
        is Resource.Empty -> {}
        is Resource.Success -> {
          _listAccount.postValue(it.data ?: mutableListOf())
        }
        else -> {}
      }
    }
  }

  fun setCategoryByType(tipeKategori: TipeKategori) = viewModelScope.launch {
    findKategoriByType.invoke(tipeKategori).collect {
      when(it) {
        is Resource.Empty -> {}
        is Resource.Success -> {
          _listCategoryByType.postValue(it.data ?: mutableListOf())
        }
        else -> {}
      }
    }
  }

  fun saveTransfer(transferModel: TransferModel) = viewModelScope.launch {
    saveTransfer.invoke(transferModel)
  }
}