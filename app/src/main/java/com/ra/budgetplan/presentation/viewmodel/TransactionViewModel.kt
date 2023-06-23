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
import com.ra.budgetplan.domain.usecase.transaksi.GetTotalTransactionByDate
import com.ra.budgetplan.domain.usecase.transaksi.pendapatan.GetPendapatanByDate
import com.ra.budgetplan.domain.usecase.transaksi.pendapatan.GetTotalPendapatanByDate
import com.ra.budgetplan.domain.usecase.transaksi.pendapatan.SavePendapatan
import com.ra.budgetplan.domain.usecase.transaksi.pengeluaran.GetPengeluaranByDate
import com.ra.budgetplan.domain.usecase.transaksi.pengeluaran.GetTotalPengeluaranByDate
import com.ra.budgetplan.domain.usecase.transaksi.pengeluaran.SavePengeluaran
import com.ra.budgetplan.domain.usecase.transaksi.transfer.GetTransferByDate
import com.ra.budgetplan.domain.usecase.transaksi.transfer.SaveTransfer
import com.ra.budgetplan.util.Resource
import com.ra.budgetplan.util.toFormatRupiah
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
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
  private val userSettingPref: UserSettingPref,
  private val getTotalPendapatanByDate: GetTotalPendapatanByDate,
  private val getTotalPengeluaranByDate: GetTotalPengeluaranByDate,
  private val getTotalTransactionByDate: GetTotalTransactionByDate
): ViewModel() {

  private var _listCategory = MutableLiveData<List<KategoriModel>>()
  val listCategory: LiveData<List<KategoriModel>> get() = _listCategory

  private var _listAccount = MutableLiveData<List<AkunModel>>()
  val listAccount: LiveData<List<AkunModel>> get() = _listAccount

  private var _listCategoryByType = MutableLiveData<List<KategoriModel>>()
  val listCategoryByType: LiveData<List<KategoriModel>> get() = _listCategoryByType

  private var _listPengeluaran = MutableLiveData<Resource<List<DetailPengeluaran>>>()
  val listPengeluaran: LiveData<Resource<List<DetailPengeluaran>>> get() = _listPengeluaran

  private val _rvIncomeState = MutableLiveData<Boolean>()
  val rvIncomeState: LiveData<Boolean> get() = _rvIncomeState

  private val _emptyIncomeLayoutState = MutableLiveData<Boolean>()
  val emptyIncomeLayoutState: LiveData<Boolean> get() = _emptyIncomeLayoutState

  private val _emptyTransferLayoutState = MutableLiveData<Boolean>()
  val emptyTransferLayoutState: LiveData<Boolean> get() = _emptyTransferLayoutState

  private val _rvTransferState = MutableLiveData<Boolean>()
  val rvTransferState: LiveData<Boolean> get() = _rvTransferState

  private var _listTransfer = MutableLiveData<Resource<List<DetailTransfer>>>()
  val listTransfer: LiveData<Resource<List<DetailTransfer>>> get() = _listTransfer

  private val _rvExpenseState = MutableLiveData<Boolean>()
  val rvExpenseState: LiveData<Boolean> get() = _rvExpenseState

  private val _emptyExpenseLayoutState = MutableLiveData<Boolean>()
  val emptyExpenseLayoutState: LiveData<Boolean> get() = _emptyExpenseLayoutState

  private val _textPengeluaran = MutableSharedFlow<String>()
  val textPengeluaran: SharedFlow<String> = _textPengeluaran.asSharedFlow()

  private val _textPendapatan = MutableSharedFlow<String>()
  val textPendapatan: SharedFlow<String> = _textPendapatan.asSharedFlow()

  private val _textTotal = MutableSharedFlow<String>()
  val textTotal: SharedFlow<String> = _textTotal.asSharedFlow()

  private var _currentDate = MutableLiveData<Pair<LocalDateTime, LocalDateTime>>()
  val currentDate: LiveData<Pair<LocalDateTime, LocalDateTime>> get() = _currentDate

  private var _incomes = MutableLiveData<Resource<List<DetailPendapatan>>>()
  val incomes: LiveData<Resource<List<DetailPendapatan>>> = _incomes


  fun setCurrentDate(localDate: Pair<LocalDateTime, LocalDateTime>) {
    _currentDate.postValue(localDate)
  }

  fun getDateViewType(): LiveData<String> =
    userSettingPref.getDateViewType().asLiveData()

  fun setStateIncomeListUi(rvState: Boolean, emptyState: Boolean) {
    _rvIncomeState.postValue(rvState)
    _emptyIncomeLayoutState.postValue(emptyState)
  }

  fun setStateExpenseListUi(rvState: Boolean, emptyState: Boolean) {
    _rvExpenseState.postValue(rvState)
    _emptyExpenseLayoutState.postValue(emptyState)
  }

  fun setStateTransferListUi(rvState: Boolean, emptyState: Boolean) {
    _rvTransferState.postValue(rvState)
    _emptyTransferLayoutState.postValue(emptyState)
  }

  fun getTotalPendapatanByDate(fromDate: LocalDateTime, toDate: LocalDateTime) {
    viewModelScope.launch {
      getTotalPendapatanByDate.invoke(fromDate, toDate)
        .onEach { _textPendapatan.emit(it.toFormatRupiah()) }
        .collect()
    }
  }

  fun getTotalPengeluaranByDate(fromDate: LocalDateTime, toDate: LocalDateTime) {
    viewModelScope.launch {
      getTotalPengeluaranByDate.invoke(fromDate, toDate)
        .onEach {  _textPengeluaran.emit(it.toFormatRupiah()) }
        .collect()
    }
  }

  fun getTotalByDate(fromDate: LocalDateTime, toDate: LocalDateTime) {
    viewModelScope.launch {
      getTotalTransactionByDate.invoke(fromDate, toDate)
        .onEach {  _textTotal.emit(it.toFormatRupiah()) }
        .collect()
    }
  }

  fun getPengeluaranByDate(fromDate: LocalDateTime, toDate: LocalDateTime) {
    viewModelScope.launch {
      val list = getPengeluaranByDate.invoke(fromDate, toDate)
      if(list.isEmpty()) _listPengeluaran.postValue(Resource.Empty(""))
      else _listPengeluaran.postValue(Resource.Success(list))
    }
  }

  fun getPendapatanByDate(fromDate: LocalDateTime, toDate: LocalDateTime)  {
    viewModelScope.launch {
      val list = getPendapatanByDate.invoke(fromDate, toDate)
      if(list.isEmpty()) _incomes.postValue(Resource.Empty(""))
      else _incomes.postValue(Resource.Success(list))
    }
  }

  fun getTransferByDate(fromDate: LocalDateTime, toDate: LocalDateTime) {
    viewModelScope.launch {
      val list = getTransferByDate.invoke(fromDate, toDate)
      _listTransfer.postValue(Resource.Loading())
      if(list.isEmpty()) _listTransfer.postValue(Resource.Empty(""))
      else _listTransfer.postValue(Resource.Success(list))
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