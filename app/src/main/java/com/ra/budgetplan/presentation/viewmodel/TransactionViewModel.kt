package com.ra.budgetplan.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ra.budgetplan.data.local.preferences.UserSettingPref
import com.ra.budgetplan.domain.entity.DetailPendapatan
import com.ra.budgetplan.domain.entity.DetailPengeluaran
import com.ra.budgetplan.domain.entity.DetailTransfer
import com.ra.budgetplan.domain.entity.TipeKategori
import com.ra.budgetplan.domain.model.AkunModel
import com.ra.budgetplan.domain.model.KategoriModel
import com.ra.budgetplan.domain.model.PendapatanModel
import com.ra.budgetplan.domain.model.PengeluaranModel
import com.ra.budgetplan.domain.model.TransferModel
import com.ra.budgetplan.domain.usecase.akun.FindAllAkun
import com.ra.budgetplan.domain.usecase.akun.FindCategoryByType
import com.ra.budgetplan.domain.usecase.transaksi.pendapatan.GetPendapatanByDate
import com.ra.budgetplan.domain.usecase.transaksi.pendapatan.SavePendapatan
import com.ra.budgetplan.domain.usecase.transaksi.pengeluaran.GetPengeluaranByDate
import com.ra.budgetplan.domain.usecase.transaksi.pengeluaran.SavePengeluaran
import com.ra.budgetplan.domain.usecase.transaksi.transfer.GetTransferByDate
import com.ra.budgetplan.domain.usecase.transaksi.transfer.SaveTransfer
import com.ra.budgetplan.util.Resource
import com.ra.budgetplan.util.RvGroup
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
  private val saveTransfer: SaveTransfer,
  private val savePengeluaran: SavePengeluaran,
  private val savePendapatan: SavePendapatan,
  private val findAllAkun: FindAllAkun,
  private val findKategoriByType: FindCategoryByType,
  private val getPengeluaranByDate: GetPengeluaranByDate,
  private val getPendapatanByDate: GetPendapatanByDate,
  private val getTransferByDate: GetTransferByDate,
  private val userSettingPref: UserSettingPref
): ViewModel() {

  private var _listCategory = MutableLiveData<List<KategoriModel>>()
  val listCategory: LiveData<List<KategoriModel>> get() = _listCategory

  private var _listAccount = MutableLiveData<List<AkunModel>>()
  val listAccount: LiveData<List<AkunModel>> get() = _listAccount

  private var _listCategoryByType = MutableLiveData<List<KategoriModel>>()
  val listCategoryByType: LiveData<List<KategoriModel>> get() = _listCategoryByType

  private var _listPengeluaran = MutableLiveData<RvGroup<String, ArrayList<DetailPengeluaran>>>()
  val listPengeluaran: LiveData<RvGroup<String, ArrayList<DetailPengeluaran>>> get() = _listPengeluaran

  private val _listPendapatan = MutableLiveData<RvGroup<String, ArrayList<DetailPendapatan>>>()
  val listPendapatan: LiveData<RvGroup<String, ArrayList<DetailPendapatan>>> get() = _listPendapatan

  private val _rvIncomeState = MutableLiveData<Boolean>()
  val rvIncomeState: LiveData<Boolean> get() = _rvIncomeState

  private val _emptyIncomeLayoutState = MutableLiveData<Boolean>()
  val emptyIncomeLayoutState: LiveData<Boolean> get() = _emptyIncomeLayoutState

  private val _emptyTransferLayoutState = MutableLiveData<Boolean>()
  val emptyTransferLayoutState: LiveData<Boolean> get() = _emptyTransferLayoutState

  private val _rvTransferState = MutableLiveData<Boolean>()
  val rvTransferState: LiveData<Boolean> get() = _rvTransferState

  private val _listTransfer = MutableLiveData<RvGroup<String, ArrayList<DetailTransfer>>>()
  val listTransfer: LiveData<RvGroup<String, ArrayList<DetailTransfer>>> get() = _listTransfer

  private val _rvExpenseState = MutableLiveData<Boolean>()
  val rvExpenseState: LiveData<Boolean> get() = _rvExpenseState

  private val _emptyExpenseLayoutState = MutableLiveData<Boolean>()
  val emptyExpenseLayoutState: LiveData<Boolean> get() = _emptyExpenseLayoutState

  private var _currentDate = MutableLiveData<LocalDate>()
  val currentDate: LiveData<LocalDate> get() = _currentDate

  fun setCurrentDate(localDate: LocalDate) {
    _currentDate.postValue(localDate)
  }

  fun getDateViewType(): LiveData<String> =
    userSettingPref.getDateViewType().asLiveData()

  fun setTransactionDate(fromDate: LocalDateTime, toDate: LocalDateTime) {
    /*
        Make the coroutines run separately.
        If not do this, the coroutine will wait for each other.
     */
    viewModelScope.launch {
      getAllTransfer(fromDate, toDate)
    }
    viewModelScope.launch {
      getAllPendapatan(fromDate, toDate)
    }
    viewModelScope.launch {
      getPengeluaranByDate(fromDate, toDate)
    }
  }

  private suspend fun getPengeluaranByDate(fromDate: LocalDateTime, toDate: LocalDateTime) {
    getPengeluaranByDate.invoke(fromDate, toDate).collect {
      when(it) {
        is Resource.Success -> {
          val monthly = RvGroup<String, ArrayList<DetailPengeluaran>>()
          for (data in it.data ?: ArrayList()) {
            val updatedAt = data.pengeluaran.createdAt
            val key = updatedAt.toLocalDate().toString()
            monthly.addIf(key, ArrayList())?.add(data)
          }
          _listPengeluaran.postValue(monthly)
          _emptyExpenseLayoutState.postValue(true)
          _rvExpenseState.postValue(false)
        }

        is Resource.Empty -> {
          _rvExpenseState.postValue(true)
          _emptyExpenseLayoutState.postValue(false)
        }

        else -> {}
      }
    }
  }

  private suspend fun getAllPendapatan(fromDate: LocalDateTime, toDate: LocalDateTime)  {
    getPendapatanByDate.invoke(fromDate, toDate).collect {
      when (it) {
        is Resource.Success -> {
          val monthly = RvGroup<String, ArrayList<DetailPendapatan>>()
          for (data in it.data ?: ArrayList()) {
            val updatedAt = data.pendapatan.createdAt
            val key = updatedAt.toLocalDate().toString()
            monthly.addIf(key, ArrayList())?.add(data)
          }
          _listPendapatan.postValue(monthly)
          _rvIncomeState.postValue(false)
          _emptyIncomeLayoutState.postValue(true)
        }

        is Resource.Empty -> {
          _rvIncomeState.postValue(true)
          _emptyIncomeLayoutState.postValue(false)
        }

        else -> {}
      }
    }
  }

  private suspend fun getAllTransfer(fromDate: LocalDateTime, toDate: LocalDateTime) {
    getTransferByDate.invoke(fromDate, toDate).collect {
      when (it) {
        is Resource.Success -> {
          val monthly = RvGroup<String, ArrayList<DetailTransfer>>()
          for (data in it.data ?: ArrayList()) {
            val updatedAt = data.transfer.createdAt
            val key = updatedAt.toLocalDate().toString()
            monthly.addIf(key, ArrayList())?.add(data)
          }
          _listTransfer.postValue(monthly)
          _rvTransferState.postValue(false)
          _emptyTransferLayoutState.postValue(true)
        }

        is Resource.Empty -> {
          _rvTransferState.postValue(true)
          _emptyTransferLayoutState.postValue(false)
        }

        else -> {}
      }
    }
  }

  fun getAllAccount() = viewModelScope.launch {
    findAllAkun.invoke().collect {
      when (it) {
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
      when (it) {
        is Resource.Empty -> {}
        is Resource.Success -> {
          _listCategoryByType.postValue(it.data ?: mutableListOf())
        }

        else -> {}
      }
    }
  }

  fun savePengeluaran(pengeluaranModel: PengeluaranModel) = viewModelScope.launch {
    savePengeluaran.invoke(pengeluaranModel)
  }

  fun savePendapatan(pendapatanModel: PendapatanModel) = viewModelScope.launch {
    savePendapatan.invoke(pendapatanModel)
  }

  fun saveTransfer(transferModel: TransferModel) = viewModelScope.launch {
    saveTransfer.invoke(transferModel)
  }
}