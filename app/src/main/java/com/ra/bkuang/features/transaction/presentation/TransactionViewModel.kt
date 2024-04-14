package com.ra.bkuang.features.transaction.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ra.bkuang.features.account.domain.model.AkunModel
import com.ra.bkuang.features.category.domain.model.KategoriModel
import com.ra.bkuang.features.transaction.domain.model.PendapatanModel
import com.ra.bkuang.features.transaction.domain.model.PengeluaranModel
import com.ra.bkuang.features.transaction.domain.model.TransferModel
import com.ra.bkuang.features.account.domain.usecase.FindAkunById
import com.ra.bkuang.features.account.domain.usecase.FindAllAkun
import com.ra.bkuang.features.account.domain.usecase.FindCategoryByType
import com.ra.bkuang.features.transaction.domain.usecase.GetTotalTransactionByDate
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.DeletePendapatanById
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.GetListDetailPendapatanByDate
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.GetPendapatanById
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.GetTotalPendapatanByDateWithFlow
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.SavePendapatan
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.UpdatePendapatan
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.DeletePengeluaranById
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.GetListDetailPengeluaranByDate
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.GetPengeluaranById
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.GetTotalPengeluaranByDateWithFlow
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.SavePengeluaran
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.UpdatePengeluaran
import com.ra.bkuang.features.transaction.domain.usecase.transfer.DeleteTransferById
import com.ra.bkuang.features.transaction.domain.usecase.transfer.GetTransferByDate
import com.ra.bkuang.features.transaction.domain.usecase.transfer.GetTransferById
import com.ra.bkuang.features.transaction.domain.usecase.transfer.SaveTransfer
import com.ra.bkuang.features.transaction.domain.usecase.transfer.UpdateTransfer
import com.ra.bkuang.common.util.ResultState
import com.ra.bkuang.core.preferences.UserSettingPref
import com.ra.bkuang.common.base.BaseViewModel
import com.ra.bkuang.features.transaction.domain.model.TransactionDetail
import com.ra.bkuang.common.util.Extension.toFormatRupiah
import com.ra.bkuang.features.transaction.data.entity.DetailPendapatan
import com.ra.bkuang.features.transaction.data.entity.DetailPengeluaran
import com.ra.bkuang.features.transaction.data.entity.DetailTransfer
import com.ra.bkuang.features.transaction.data.entity.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
  private val saveTransfer: SaveTransfer,
  private val savePengeluaran: SavePengeluaran,
  private val savePendapatan: SavePendapatan,
  private val findAllAkun: FindAllAkun,
  private val findKategoriByType: FindCategoryByType,
  private val getListDetailPengeluaranByDate: GetListDetailPengeluaranByDate,
  private val getListDetailPendapatanByDate: GetListDetailPendapatanByDate,
  private val getTransferByDate: GetTransferByDate,
  private val userSettingPref: UserSettingPref,
  private val getTotalPendapatanByDateWithFlow: GetTotalPendapatanByDateWithFlow,
  private val getTotalPengeluaranByDateWithFlow: GetTotalPengeluaranByDateWithFlow,
  private val getTotalTransactionByDate: GetTotalTransactionByDate,
  private val deletePengeluaranById: DeletePengeluaranById,
  private val deleteTransferById: DeleteTransferById,
  private val deletePendapatanById: DeletePendapatanById,
  private val getPendapatanById: GetPendapatanById,
  private val getPengeluaranById: GetPengeluaranById,
  private val getTransferById: GetTransferById,
  private val updateTransfer: UpdateTransfer,
  private val updatePendapatan: UpdatePendapatan,
  private val updatePengeluaran: UpdatePengeluaran,
  private val findAkunById: FindAkunById
): BaseViewModel() {

  private var _saveTransactionState = MutableLiveData<Boolean>()
  val saveTransactionState: LiveData<Boolean> = _saveTransactionState

  private var _pendapatanModel = MutableLiveData<PendapatanModel>()
  val pendapatanModel: LiveData<PendapatanModel> = _pendapatanModel

  private var _pengeluaranModel = MutableLiveData<PengeluaranModel>()
  val pengeluaranModel: LiveData<PengeluaranModel> = _pengeluaranModel

  private var _transferModel = MutableLiveData<TransferModel>()
  val transferModel: LiveData<TransferModel> = _transferModel

  private var _listAccount = MutableLiveData<List<AkunModel>>()
  val listAccount: LiveData<List<AkunModel>> get() = _listAccount

  private var _listCategoryByType = MutableLiveData<List<KategoriModel>>()
  val listCategoryByType: LiveData<List<KategoriModel>> get() = _listCategoryByType

  private var _listPengeluaran = MutableLiveData<ResultState<List<DetailPengeluaran>>>()
  val listPengeluaran: LiveData<ResultState<List<DetailPengeluaran>>> get() = _listPengeluaran

  private val _rvIncomeState = MutableLiveData<Boolean>()
  val rvIncomeState: LiveData<Boolean> get() = _rvIncomeState

  private val _emptyIncomeLayoutState = MutableLiveData<Boolean>()
  val emptyIncomeLayoutState: LiveData<Boolean> get() = _emptyIncomeLayoutState

  private val _emptyTransferLayoutState = MutableLiveData<Boolean>()
  val emptyTransferLayoutState: LiveData<Boolean> get() = _emptyTransferLayoutState

  private val _rvTransferState = MutableLiveData<Boolean>()
  val rvTransferState: LiveData<Boolean> get() = _rvTransferState

  private var _listTransfer = MutableLiveData<ResultState<List<DetailTransfer>>>()
  val listTransfer: LiveData<ResultState<List<DetailTransfer>>> get() = _listTransfer

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

  private var _incomes = MutableLiveData<ResultState<List<DetailPendapatan>>>()
  val incomes: LiveData<ResultState<List<DetailPendapatan>>> = _incomes

  private var _detailTransaction = MutableLiveData<TransactionDetail>()
  val detailTransaction: LiveData<TransactionDetail> = _detailTransaction

  private val _saveTransactionDialogStateUi = MutableLiveData<Boolean>()
  val saveTransactionDialogStateUi: LiveData<Boolean> = _saveTransactionDialogStateUi

  private val _updateTransactionState = MutableLiveData<Boolean>()
  val updateTransactionState: LiveData<Boolean> = _updateTransactionState

  fun setSaveTransactionState(state: Boolean) {
    _saveTransactionState.postValue(state)
  }

  fun setUpdateTransctionState(state: Boolean) {
    _updateTransactionState.postValue(state)
  }

  fun setSaveTransactionDialogStateUi(state: Boolean) {
    _saveTransactionDialogStateUi.postValue(state)
  }

  fun checkAccountMoney(idAkun: UUID, amount: Int, action: suspend () -> Unit = {}) {
    viewModelScope.launch {
      val account = findAkunById.invoke(idAkun)
      if(account.total - amount >= 0) {
        action()
        _saveTransactionDialogStateUi.postValue(false)
      } else {
        _saveTransactionDialogStateUi.postValue(true)
      }
    }
  }

  fun getPendapatanById(uuid: UUID) {
    viewModelScope.launch {
      val data = getPendapatanById.invoke(uuid)
      _pendapatanModel.postValue(data)
    }
  }

  fun getPengeluaranById(uuid: UUID) {
    viewModelScope.launch {
      val data = getPengeluaranById.invoke(uuid)
      _pengeluaranModel.postValue(data)
    }
  }

  fun getTransferById(uuid: UUID) {
    viewModelScope.launch {
      val data = getTransferById.invoke(uuid)
      _transferModel.postValue(data)
    }
  }

  fun setDetailTransaction(detail: TransactionDetail) {
    _detailTransaction.postValue(detail)
  }

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
      getTotalPendapatanByDateWithFlow.invoke(fromDate, toDate)
        .onEach { _textPendapatan.emit(it.toFormatRupiah()) }
        .collect()
    }
  }

  fun getTotalPengeluaranByDate(fromDate: LocalDateTime, toDate: LocalDateTime) {
    viewModelScope.launch {
      getTotalPengeluaranByDateWithFlow.invoke(fromDate, toDate)
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
      val list = getListDetailPengeluaranByDate.invoke(fromDate, toDate)
      if(list.isEmpty()) _listPengeluaran.postValue(ResultState.Empty)
      else _listPengeluaran.postValue(ResultState.Success(list))
    }
  }

  fun getPendapatanByDate(fromDate: LocalDateTime, toDate: LocalDateTime)  {
    viewModelScope.launch {
      val list = getListDetailPendapatanByDate.invoke(fromDate, toDate)
      if(list.isEmpty()) _incomes.postValue(ResultState.Empty)
      else _incomes.postValue(ResultState.Success(list))
    }
  }

  fun getTransferByDate(fromDate: LocalDateTime, toDate: LocalDateTime) {
    viewModelScope.launch {
      val list = getTransferByDate.invoke(fromDate, toDate)
      _listTransfer.postValue(ResultState.Loading)
      if(list.isEmpty()) _listTransfer.postValue(ResultState.Empty)
      else _listTransfer.postValue(ResultState.Success(list))
    }
  }

  fun getAllAccount() = viewModelScope.launch {
    _listAccount.postValue(findAllAkun.invoke())
  }

  fun setCategoryByType(tipeKategori: TransactionType) = viewModelScope.launch {
    findKategoriByType.invoke(tipeKategori).collect {
      when (it) {
        is ResultState.Empty -> {}
        is ResultState.Success -> {
          _listCategoryByType.postValue(it.data ?: mutableListOf())
        }
        else -> {}
      }
    }
  }

  suspend fun savePengeluaran(pengeluaranModel: PengeluaranModel) =
    savePengeluaran.invoke(pengeluaranModel)


  suspend fun savePendapatan(pendapatanModel: PendapatanModel) =
    savePendapatan.invoke(pendapatanModel)


  suspend fun saveTransfer(transferModel: TransferModel) =
    saveTransfer.invoke(transferModel)


  suspend fun deletePendapatanById(uuid: UUID) =
    deletePendapatanById.invoke(uuid)


  suspend fun deletePengeluaranById(uuid: UUID) =
    deletePengeluaranById.invoke(uuid)


  suspend fun deleteTransferById(uuid: UUID) =
    deleteTransferById.invoke(uuid)


  suspend fun updatePendapatan(newPendapatanModel: PendapatanModel, oldPendapatanModel: PendapatanModel) =
    updatePendapatan.invoke(newPendapatanModel, oldPendapatanModel)


  suspend fun updatePengeluaran(newPengeluaranModel: PengeluaranModel, oldPengeluaranModel: PengeluaranModel) =
    updatePengeluaran.invoke(newPengeluaranModel,oldPengeluaranModel)

  suspend fun updateTransfer(newTransferModel: TransferModel, oldTransferModel: TransferModel)=
    updateTransfer.invoke(newTransferModel, oldTransferModel)
}